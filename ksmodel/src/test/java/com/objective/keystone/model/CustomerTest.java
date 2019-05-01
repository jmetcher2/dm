package com.objective.keystone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;
import com.objective.keystone.model.folder.FolderType;

import au.id.lagod.dm.test.TextKeyCollectionPersistenceTests;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class CustomerTest extends TextKeyCollectionPersistenceTests<Customer> {
	@Autowired
	protected Model model;

	@Override
	protected CustomerManager getChildObjectManager() {
		return model.getCustomers();
	}

	@Override
	protected Customer createChildObject(String name) {
		Customer c = super.createChildObject(name);
		
		c.getMetadata().put("V5enabled", true);
		
		return c;
	}
	
	@Override 
	protected void checkCreatedObject(Customer createdObject) {
		assertTrue(createdObject.getMetadata().containsKey("V5enabled"));
		assertEquals(FolderType.consult_root.name(), createdObject.getFolders().getConsultRoot().getType());
		assertEquals(FolderType.root.name(), createdObject.getFolders().getRoot().getType());
	}
	
	

}
