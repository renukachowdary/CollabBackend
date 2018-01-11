package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.FriendDao;
import com.niit.model.Error;

import com.niit.model.Friend;

import com.niit.model.UsersDetails;

@Controller
public class FriendController {
	
	@Autowired
	private FriendDao friendDao;
	
	@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)
	public ResponseEntity<?> getSuggestedUsers(HttpSession session)
	{
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null)
		{
			Error error = new Error(5, "UnAuthorized Access");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";
		List<UsersDetails> suggestedUsers=friendDao.suggestedUsersList(validUser.getUsername());
		return new ResponseEntity<List<UsersDetails>>(suggestedUsers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/addfriendrequest/{toId}",method=RequestMethod.POST)
	public ResponseEntity<?> addFriendRequest(@PathVariable String toId,HttpSession session)
	{
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null)
		{
			Error error = new Error(5, "UnAuthorized Access");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		Friend friend=new Friend();
		friend.setFromId(validUser.getUsername());
		friend.setToId(toId);
		friend.setStatus('P');
		friendDao.addFriendRequest(friend);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
	public ResponseEntity<?> pendingRequests(HttpSession session)
	{
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null)
		{
			Error error = new Error(5, "UnAuthorized Access");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		
		//String username ="renuka";
		List<Friend> pendingRequests=friendDao.pendingRequests(validUser.getUsername());
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/updatependingrequest",method=RequestMethod.PUT)
	public ResponseEntity<?> updatePendingRequest(@RequestBody Friend friend,HttpSession session)
	{
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null)
		{
			Error error = new Error(5, "UnAuthorized Access");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		friendDao.updatePendingRequest(friend); //updated status[a/r]
		return new ResponseEntity<Void>(HttpStatus.OK);		
	}
	
	@RequestMapping(value="/getfriends",method=RequestMethod.GET)
	public ResponseEntity<?> getListofFriends(HttpSession session)
	{
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null)
		{
			Error error = new Error(5, "UnAuthorized Access");
			return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED);
		}
		List<UsersDetails> friends=friendDao.listofFriends(validUser.getUsername());
		return new ResponseEntity<List<UsersDetails>>(friends,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/mutualfriends/{toId}",method=RequestMethod.GET)
	public ResponseEntity<?> getListofMutualFriends(@PathVariable String toId ,HttpSession session)
	{
		/*String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClass error = new ErrorClass(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClass>(error, HttpStatus.UNAUTHORIZED);
		}*/
		String username="ser";
		List<UsersDetails> mutualfriends=friendDao.listofMutualFriends(username, toId);
		return new ResponseEntity<List<UsersDetails>>(mutualfriends,HttpStatus.OK);
		
	}
	

}