package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.NotificationDao;
import com.niit.model.Error;
import com.niit.model.Notification;
import com.niit.model.UsersDetails;

@RestController
public class NotificationController {

	
	@Autowired
	private NotificationDao notificationDao;
	
	@RequestMapping(value="/getnotification/{viewed}")
	public ResponseEntity<?>getNotification(@PathVariable String username, int viewed,HttpSession session){
	
		UsersDetails validUser=(UsersDetails)session.getAttribute("validUser");
		if(validUser==null)
		{
			Error error=new Error(5,"unAuthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
	List<Notification> notifications=notificationDao.getNotification(username, viewed);
	return new ResponseEntity<List<Notification>>(notifications,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatenotification/{notificationId}")
public ResponseEntity<?>updateNotification(@PathVariable int notificationId,HttpSession session){
		UsersDetails validUser=(UsersDetails)session.getAttribute("validUser");
		if(validUser==null)
		{
			Error error=new Error(5,"unAuthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		Notification notification=notificationDao.updateNotification(notificationId);
		return new ResponseEntity<Notification>(notification,HttpStatus.OK);
	}
}