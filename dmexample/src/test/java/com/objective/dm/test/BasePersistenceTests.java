package com.objective.dm.test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.Utility;


public abstract class BasePersistenceTests<ObjectType extends BaseDomainObject> extends BaseTransactionalContextTests {

	@Resource
	protected org.springframework.orm.hibernate5.HibernateTransactionManager txManager;
	@Resource
	protected SessionFactory sf;
	protected ObjectType domainObject;

	public BasePersistenceTests() {
		super();
	}

	abstract protected void doSetupBeforeTransaction();

	abstract protected void doTeardownAfterTransaction();

	@BeforeTransaction
	public void setupBeforeTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("SomeTxName");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	
		TransactionStatus status = txManager.getTransaction(def);
		
		//Session session = sf.openSession();
		
		try {
			doSetupBeforeTransaction();
		
			txManager.commit(status);
			
		}
		catch (javax.validation.ConstraintViolationException e) {
			Utility.printMessages(e);
			
			txManager.rollback(status);
			
			throw new Error (e);
		}
		catch (java.lang.Error e) {
			txManager.rollback(status);
			
			throw new Error(e);
		}
		
		//session.close();
	}

	@AfterTransaction
	public void teardownAfterTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("SomeTxName");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	
		TransactionStatus status = txManager.getTransaction(def);
		
		//Session session = sf.openSession();
		
		
		try {
			doTeardownAfterTransaction();
			
			txManager.commit(status);
		
		}
		catch (javax.validation.ConstraintViolationException e) {
			Utility.printMessages(e);
			
			if (!status.isCompleted()) {
				txManager.rollback(status);
			}
			
			throw (e);
		}
		catch (java.lang.Error e) {
			txManager.rollback(status);
			
			throw new Error(e);
		}
			
		//session.close();
	}

}