package au.id.lagod.dm.test;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
abstract public class BaseTransactionalContextTests extends  AbstractTransactionalJUnit4SpringContextTests   {
	
	@Autowired
	private SessionFactory sf;
	public SessionFactory getSf() {
		return sf;
	}
		  
} 
