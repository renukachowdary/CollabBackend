/*package com.niit.Backend2;

import static org.junit.Assert.*;
import org.junit.BeforeClass;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.niit.configuration.DBConfiguartion;
import com.niit.dao.UserDao;
import com.niit.model.User1;
@ComponentScan("com.niit.*")
public class UserTest {

static UserDao  userDao;
	
	@BeforeClass
	public static void initialize()
	{
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.register(DBConfiguartion.class);
		context.scan("com.niit.*");
		context.refresh();
		userDao=(UserDao)context.getBean("userDao");
	}
	
	
	
	@Test
	public void saveUser1Test()
	{
		User1 user1=new User1();
		user1.setFirstName("renu");
		user1.setLastName("donelli");
		user1.setContact("9501841426");
		user1.setEmail("renu@gmail.com");
		user1.setPassword("123");
		user1.setRole("Admin");
		assertTrue("Problem in Inserting user", userDao.saveOrUpdate(user1));
	}
}
*/