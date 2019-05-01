package com.objective.keystone.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.event.Event;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.BaseChildObjectPersistenceTests;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class EventTest extends BaseChildObjectPersistenceTests<Event, Customer> {
	@Autowired
	protected Model model;

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
