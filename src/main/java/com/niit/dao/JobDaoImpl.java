package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Job;

@Repository
public class JobDaoImpl implements JobDao 
{
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public JobDaoImpl(SessionFactory sessionFactory) {
		
		this.sessionFactory=sessionFactory;
	}

	public void saveJob(Job job) {
		 Session session=sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 session.saveOrUpdate(job);
		 tx.commit();
		 
		 return; 
		}
	
	@Transactional
	public List<Job> getAllJobs() 
	{
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();
		session.close();
		return jobs;
	}
	
	
	public Job getJobById(int id) {
        Session session=sessionFactory.openSession();
		 Transaction tx=session.beginTransaction();
		 Job job=(Job)session.get(Job.class, id);
      
        tx.commit();
        session.close();
        return job;
    }

	}