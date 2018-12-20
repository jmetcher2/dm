package com.objective.keystone.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.objective.dm.base.DomainCollectionManager;
import com.objective.dm.base.DomainObjectManager;
import com.objective.dm.test.BaseChildObjectPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

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
	protected DomainCollectionManager<Group> getChildObjectManager() {
		return getParent().getGroups();
	}
	
	@Test
	public void testFolders() {
		Group g = getChildObject();
		Folder f = getParent().getFolders().create("testFolder");
		
		assertTrue(g.getFolders().isEmpty());
		g.getFolders().add(f);
		assertFalse(g.getFolders().isEmpty());
		g.getFolders().remove(f);
		getParent().getGroups().remove(g);
		assertTrue(g.getFolders().isEmpty());
		
	}

}
