package com.objective.keystone.model;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.dm.test.BaseChildObjectPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.event.Event;

public class EventTest extends BaseChildObjectPersistenceTests<Event, Customer> {

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
	protected void removeParent() {
		model.getCustomers().remove(getParent());
	}

	@Override
	public Customer getParent() {
		return model.customers(getParentName());
	}

	@Override
	protected DomainObjectCollectionManager<Event> getChildObjectManager() {
		return getParent().getEvents();
	}


}
