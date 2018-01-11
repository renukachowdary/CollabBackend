package com.niit.dao;

import com.niit.model.ProfilePicture;

public interface ProfilePictureDAO {

	
	public ProfilePicture getProfilePicture(String username);
	public void saveProfilePicture(ProfilePicture profilePicture);
}