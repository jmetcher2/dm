package com.objective.keystone.model;

import com.objective.dm.test.BaseChildObjectPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.Group;

import au.id.lagod.dm.base.DomainObjectManager;

public class GroupTest extends BaseChildObjectPersistenceTests<Group, Customer> {
	
	

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
	protected DomainObjectManager<Group> getChildObjectManager() {
		return getParent().getGroups();
	}

}
