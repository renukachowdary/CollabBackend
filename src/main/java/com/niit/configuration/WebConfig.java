package com.niit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc //<mvc:annotation-driven>
@ComponentScan(basePackages="com.niit.*") //<context:component-scan>
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter{
	

	public void addResourceHandlers(ResourceHandlerRegistry registry){
	registry.addResourceHandler("/resources/**")
	.addResourceLocations("/WEB-INF/resources/");
	} 
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver getMultipartResolver()
	{
		CommonsMultipartResolver commonResolver=new CommonsMultipartResolver();
		commonResolver.setMaxUploadSize(20971520); // 20MB
		commonResolver.setMaxInMemorySize(1048576);	
		return commonResolver;
	}
}
