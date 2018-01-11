package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePictureDAO;
import com.niit.model.ProfilePicture;
import com.niit.model.UsersDetails;
import com.niit.model.UsersDetails;

@RestController
public class ProfilePictureController {
	@Autowired
	ProfilePictureDAO profilePictureDAO;	
	
	@PostMapping("/doUpload")
	public ResponseEntity<?> uploadProfilePicture(@RequestParam(value="file") CommonsMultipartFile fileUpload,HttpSession session)
	{
		
		UsersDetails validUser=(UsersDetails)session.getAttribute("validUser");
		if(validUser==null)		{
			   Error error=new Error("UnAuthorized user");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		} 
		ProfilePicture profilePicture=new ProfilePicture();
		profilePicture.setUsername(validUser.getUsername());
		profilePicture.setImage(fileUpload.getBytes());
		profilePictureDAO.saveProfilePicture(profilePicture);
		return new ResponseEntity<UsersDetails>(validUser,HttpStatus.OK);
	}
		
		
		@GetMapping(value="/getimage/{username}")
		public @ResponseBody byte[] getProfilePic(@PathVariable String username,HttpSession session){
			UsersDetails validUser=(UsersDetails)session.getAttribute("validUser");
			if(validUser==null)
				return null;
			else
			{
				ProfilePicture profilePic=profilePictureDAO.getProfilePicture(username);
				if(profilePic==null)
					return null;
				else
					return profilePic.getImage();
			}
			
	}
		
}