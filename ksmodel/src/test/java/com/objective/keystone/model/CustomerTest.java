package com.objective.keystone.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.test.TextKeyCollectionPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class CustomerTest extends TextKeyCollectionPersistenceTests<Customer> {
	@Autowired
	protected Model model;

	@Override
	protected CustomerManager getChildObjectManager() {
		return model.getCustomers();
	}

}
