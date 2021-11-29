package au.id.lagod.dm.test;

import javax.annotation.Resource;

import org.aspectj.lang.Aspects;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import au.id.lagod.dm.base.BaseDomainObject;


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
		DoInTransaction.doAction(txManager, () -> doSetupBeforeTransaction());
	}

	@AfterTransaction
	public void teardownAfterTransaction() {
		DoInTransaction.doAction(txManager, () -> doTeardownAfterTransaction());
	}
	
	@AfterClass
	public static void afterClass() {
		// Spring test framework has a habit of caching and switching contexts without warning,
		// meaning static configuration (like aspects) can get out of sync
		// Destroying aspect state after each test ensures that it will be recreated from 
		// the current context
		Aspects.aspectOf(AnnotationBeanConfigurerAspect.class).destroy();
		Aspects.aspectOf(AnnotationTransactionAspect.class).destroy();
	}
	
	

}