package com.objective.keystone.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.BaseChildObjectPersistenceTests;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class GroupTest extends BaseChildObjectPersistenceTests<Group, Customer> {
	@Autowired
	protected Model model;
	
	@Override
	protected void doSetupBeforeTransaction() {
		model.getCustomers().create(getParentName());
		super.doSetupBeforeTransaction();
		Folder f = getParent().getFolders().create("testFolder");
		getChildObject().getGroupFolders().create(f);
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
	protected DomainObjectCollectionManager<Group> getChildObjectManager() {
		return getParent().getGroups();
	}
	
	@Test
	public void testFolders() {
		Folder f = getParent().folders("testFolder");
		Group g = getChildObject();
		
		assertFalse(getChildObject().getGroupFolders().isEmpty());
		g.getGroupFolders().removeAssociate(f);
		getParent().getGroups().remove(g);
		assertTrue(g.getGroupFolders().isEmpty());
		
	}

}
