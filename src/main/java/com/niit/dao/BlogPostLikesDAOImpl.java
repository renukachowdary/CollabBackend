package com.niit.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.UsersDetails;
@Repository

public class BlogPostLikesDAOImpl implements BlogPostLikesDAO{

	@Autowired
	private SessionFactory sessionFactory;
	@Transactional
		public BlogPostLikes userLike(BlogPost blogPost, UsersDetails user) {
			Session session=sessionFactory.getCurrentSession();
			//select * from blogpostlikes_s180133 where blogpost_id=? and user_username=?
			Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.username=? ");
			System.out.println("BlogPost id  " + blogPost.getId());
			System.out.println("Username " + user.getUsername());
			query.setInteger(0, blogPost.getId());
			query.setString(1, user.getUsername());
			//blogPostlikes = null [glyphicon in black color] / 1 [glyphicon in blue color] object
			BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
			System.out.println(blogPostLikes);
			return blogPostLikes;
			
		}
	
	@Transactional
		public BlogPost updateLike(BlogPost blogPost, UsersDetails user) {
			Session session=sessionFactory.getCurrentSession();
			BlogPostLikes blogPostLikes=userLikes(blogPost,user);
			//insert and increment  / delete and decrement
			//like
			if(blogPostLikes==null){ //insert into blogpostlikes, increment blogpost.likes
				BlogPostLikes insertLikes=new BlogPostLikes();
				insertLikes.setBlogPost(blogPost);//FK blogpost_id
				insertLikes.setUser(user);//FK user_username
				session.save(insertLikes); //insert into blogpostlikes
				blogPost.setLikes(blogPost.getLikes() + 1); //increment the number of likes
				session.update(blogPost);//update blogpost set likes=.. where id=?
			}
			else{//unlike
				session.delete(blogPostLikes); //delete from blogpostlikes
				blogPost.setLikes(blogPost.getLikes()-1); //decrement the number of likes
				session.merge(blogPost); //update blogpost set likes ...
			}
			return blogPost;
		}

	public BlogPostLikes userLikes(BlogPost blogPost, UsersDetails user) {
		// TODO Auto-generated method stub
		return null;
	}

	public BlogPost updateLikes(BlogPost blogPost, UsersDetails user) {
		// TODO Auto-generated method stub
		return null;
	}

		
		
}