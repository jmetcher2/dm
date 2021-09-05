package au.id.lagod.dm.test.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.BaseChildObjectPersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.group.Group;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class GroupTest extends BaseChildObjectPersistenceTests<Group, Customer> {

	@Autowired
	protected ExampleModel model;
	
	@Override
	protected void doSetupBeforeTransaction() {
		model.getCustomers().create(getParentName());
		super.doSetupBeforeTransaction();
	}

	@Override
	protected void doTeardownAfterTransaction() {
		super.doTeardownAfterTransaction();
		removeParent();
	}

	@Override
	public Customer getParent() {
		return model.customers(getParentName());
	}

	@Override
	protected void removeParent() {
		model.getCustomers().remove(getParent());
		
	}

	@Override
	protected DomainObjectCollectionManager<Group> getChildObjectManager() {
		return getParent().getGroups();
	}

}
