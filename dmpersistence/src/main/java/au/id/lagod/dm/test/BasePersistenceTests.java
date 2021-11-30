package au.id.lagod.dm.test;

import javax.annotation.Resource;

import org.aspectj.lang.Aspects;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import au.id.lagod.dm.base.BaseDomainObject;

@DirtiesContext
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
		// meaning context can cross-polute their static configuration (like aspects). 
		// Setting the @DirtiesContext flag, and destroying aspect state after each test ensures that we 
		// get a clean context for each test class
		Aspects.aspectOf(AnnotationBeanConfigurerAspect.class).destroy();
		Aspects.aspectOf(AnnotationTransactionAspect.class).destroy();
	}
	
	

}