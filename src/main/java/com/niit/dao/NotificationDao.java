package com.niit.dao;

import java.util.List;

import com.niit.model.Notification;

public interface NotificationDao {

	
	public List<Notification>getNotification(String username,int viewed);
    public Notification updateNotification(int notificationId);
}
