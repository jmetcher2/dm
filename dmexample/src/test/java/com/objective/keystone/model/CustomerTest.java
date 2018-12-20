package com.objective.keystone.model;

import com.objective.dm.test.TextKeyCollectionPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

public class CustomerTest extends TextKeyCollectionPersistenceTests<Customer> {

	@Override
	protected CustomerManager getChildObjectManager() {
		return model.getCustomers();
	}

}
