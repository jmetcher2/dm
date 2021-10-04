package au.id.lagod.dm.test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

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

}