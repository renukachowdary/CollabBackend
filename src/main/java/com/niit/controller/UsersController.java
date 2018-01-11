package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.model.UsersDetails;
import com.niit.service.UsersService;


@RestController
public class UsersController {

	@Autowired
	private UsersService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody UsersDetails user) {
	
		if(!userService.isUsernameValid(user.getUsername()))
		{
			Error error = new Error(user.getUsername()+"..username already exists,, please enter different username");
			return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		
	 if (!userService.isEmailValid(user.getEmail()))
	 {
			Error error = new Error(user.getEmail()+"...Email address already exists,, please enter different email");
			return new ResponseEntity<Error>(error, HttpStatus.NOT_ACCEPTABLE);
	}

		boolean result = userService.saveOrUpdate(user);
		if (result) {
			return new ResponseEntity<UsersDetails>(user, HttpStatus.OK);
		} else {
			Error error = new Error("unable to register user details");
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/************************Login***********************************/
	@SuppressWarnings("unused")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UsersDetails validUser,HttpSession session){
	
		
		UsersDetails u =userService.getUser(validUser.getUsername());
		
		System.out.println(u.getUsername());
		
		
      validUser=userService.login(u.getUsername(),u.getPassword());
      System.out.println((validUser));

	   System.out.println(validUser.getUsername());
	    System.out.println("Password = "+validUser.getPassword());
		
	    if(validUser==null)

	    {
		    Error error=new Error("Invalid username and password.. please enter valid credentials");
		    return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
	        validUser.setOnline(true);
		    validUser=userService.updateUser(validUser);
		    session.setAttribute("validUser", validUser);
		    return new ResponseEntity<UsersDetails>(u,HttpStatus.OK);    
		}
	   
	}
	/************************Logout ***********************************/
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{ 
	  
	    UsersDetails validUser=(UsersDetails) session.getAttribute("validUser");
	    if(validUser==null)

	    {
	    	Error error=new Error("Unauthorized user");
		    return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}	   
	    else	
	    {
	        validUser.setOnline(false);
	        userService.updateUser(validUser);
		    session.removeAttribute("validUser");
		    session.invalidate();
		    return new ResponseEntity<UsersDetails>(validUser,HttpStatus.OK);    
		}
	}
/******************Listing the User Details****************************/
	
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	   public ResponseEntity<List<UsersDetails>> listAllUsers() {
        List<UsersDetails> users = userService.UserList();
        if(users.isEmpty()){
            return new ResponseEntity<List<UsersDetails>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<UsersDetails>>(users, HttpStatus.OK);
    }
  
	@RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody UsersDetails user,HttpSession session)
	{    
	    UsersDetails validuser=(UsersDetails)session.getAttribute("validuser");
	    if(validuser==null)
	    {
	        Error error=new Error("Unauthorized user");
	        return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED); 
	    }
	    userService.updateUser(validuser);
	    session.setAttribute("user", validuser);
	    return new ResponseEntity<Void>(HttpStatus.OK);
	}

	
}