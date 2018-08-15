package com.objective.dm.test;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.objective.keystone.config.AppConfig;
import com.objective.keystone.model.Model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
abstract public class BaseTransactionalContextTests extends  AbstractTransactionalJUnit4SpringContextTests   {
	
	@Autowired
	protected Model model;
	@Autowired
	private SessionFactory sf;
	public SessionFactory getSf() {
		return sf;
	}
		  
} 
