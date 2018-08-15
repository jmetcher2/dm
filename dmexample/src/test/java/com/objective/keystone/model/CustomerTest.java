package com.objective.keystone.model;

import com.objective.dm.test.BaseCollectionPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

public class CustomerTest extends BaseCollectionPersistenceTests<Customer> {

	@Override
	protected CustomerManager getChildObjectManager() {
		return model.getCustomers();
	}

}
