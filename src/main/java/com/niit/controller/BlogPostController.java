package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogPostDAO;
import com.niit.dao.BlogPostLikesDAO;
import com.niit.dao.UsersDAO;
import com.niit.model.*;
import com.niit.model.Error;
@RestController
public class BlogPostController {
	@Autowired
	private BlogPostDAO blogPostDao;
	@Autowired
	private UsersDAO userDAO;
	@Autowired
	private BlogPostLikesDAO blogPostLikesDAO;
	
	
	@RequestMapping(value = "/saveblog", method = RequestMethod.POST)
	public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost, HttpSession session) {
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if (validUser == null) {
			Error error = new Error(5, "Unauthorized access");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	  UsersDetails userDetails = userDAO.getUserByUsername(validUser.getUsername());// select * from user
														// where username='adam'
		blogPost.setPostedOn(new Date());
		blogPost.setPostedBy(userDetails);// FK column postedby_username ['adam']
		try {
			blogPostDao.saveBlogPost(blogPost);
		} catch (Exception e) {
			Error error = new Error(6, "Unable to insert blog details " + e.getMessage());
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
	}

	
	
	
	@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(@PathVariable int approved,HttpSession session){
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if(validUser==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		if(approved==0){//list of blogs waiting for approval
			UsersDetails user=userDAO.getUserByUsername(validUser.getUsername());
		if(!user.getRole().equals("ADMIN")){
			Error error=new Error(7,"Access Denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		}
		List<BlogPost> blogPosts=blogPostDao.getBlogs(approved);
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
		
	}
	@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable int id){
//		String username=(String)session.getAttribute("username");
//		if(username==null){
//			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
//			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
//		}
		BlogPost blogPost=blogPostDao.getBlogById(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/updateapprovalstatus",method=RequestMethod.PUT)
	public ResponseEntity<?> updateApprovalStatus(@RequestBody BlogPost blogPost,
			          @RequestParam(required=false) String rejectionReason,HttpSession session){
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if(validUser==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		try{
			//if admin selects Approve, blogPost.approved=1
			//if admin selects Reject, blogPost.approved=0
			System.out.println(blogPost);
		blogPostDao.updateBlogPost(blogPost,rejectionReason);
		}catch(Exception e){
			Error error=new Error(7,"Unable to update blogpost approval status "+e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value="/userLikes/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> userLikes(@PathVariable int id,HttpSession session){
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if(validUser==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		UsersDetails usersDetails=userDAO.getUserByUsername(validUser.getUsername());
		BlogPost blogPost=blogPostDao.getBlogById(id);
		//blogPostLikes = null / 1 object
		// if user has not yet liked the blogPost, blogPostLikes = null
		//if user has liked the blogPost already, blogPostLikes= 1 object
		BlogPostLikes blogPostLikes=blogPostLikesDAO.userLikes(blogPost, usersDetails);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
	}
	@RequestMapping(value="/updatelikes",method=RequestMethod.PUT)
	public ResponseEntity<?> updateLikes(@RequestBody BlogPost blogPost,HttpSession session){
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if(validUser==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		UsersDetails usersDetails=userDAO.getUserByUsername(validUser.getUsername());
		BlogPost updatedBlogPost=blogPostLikesDAO.updateLikes(blogPost, usersDetails);
		return new ResponseEntity<BlogPost>(updatedBlogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/addcomment",method=RequestMethod.POST)
	//http://localhost:8082/Backend2/addcomment?commentText='Thanks'&id=484
	public ResponseEntity<?> addBlogComment(@RequestParam String commentText , @RequestParam int id
			,HttpSession session){
		UsersDetails validUser = (UsersDetails) session.getAttribute("validUser");
		if(validUser==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
		}
		UsersDetails commentedBy=userDAO.getUserByUsername(validUser.getUsername());
		//Construct blogcomment object
		BlogComment blogComment=new BlogComment();
		
		blogComment.setCommentText(commentText);
		blogComment.setCommentedBy(commentedBy);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		blogComment.setBlogPost(blogPost);
		blogComment.setCommentedOn(new Date());
		
		
		try{
		blogPostDao.addComment(blogComment);
		}catch(Exception e){
			Error error=new Error(7,"Unable to post comments " + e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
		 blogPost=blogPostDao.getBlogById(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		
	}

}