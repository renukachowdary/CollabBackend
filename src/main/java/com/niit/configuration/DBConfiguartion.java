package com.niit.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import com.niit.model.BlogPost;
import com.niit.model.Friend;
import com.niit.model.Job;
import com.niit.model.Notification;
import com.niit.model.ProfilePicture;
import com.niit.model.UsersDetails;



public class DBConfiguartion {
	// user Database
			@Bean(name = "dataSource")
			public DataSource getDataSource() {
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
				dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
				dataSource.setUsername("dtproject2");
				dataSource.setPassword("pass");
				System.out.println("Datasource");
				return dataSource;

			}

			private Properties getHibernateProperties() {
				Properties properties = new Properties();
				properties.put("hibernate.show_sql", "true");
				properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
			 	//properties.put("hibernate.hbm2ddl.auto", "create");
		        properties.put("hibernate.hbm2ddl.auto", "update");
				System.out.println("Hibernate Properties");
				return properties;

			}
			//Session Factory Bean Created.
			@Autowired
			@Bean(name = "sessionFactory")
			public SessionFactory getSessionFactory(DataSource dataSource) {
				LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
				sessionBuilder.addProperties(getHibernateProperties());
				sessionBuilder.addAnnotatedClasses(BlogPost.class);
				sessionBuilder.addAnnotatedClasses(UsersDetails.class);
				sessionBuilder.addAnnotatedClass(Job.class);
				sessionBuilder.addAnnotatedClass(Notification.class);
				sessionBuilder.addAnnotatedClass(ProfilePicture.class);
				sessionBuilder.addAnnotatedClass(Friend.class);
				sessionBuilder.scanPackages("com.niit");
				
				System.out.println("Session");
				return sessionBuilder.buildSessionFactory();
				
			}
			//Transaction Bean Object
			@Autowired
			@Bean(name = "transactionManager")
			public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
				HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
				System.out.println("Transaction");
				return transactionManager;
			}
			
			
}