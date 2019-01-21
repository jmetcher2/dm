package com.objective.keystone.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.objective.dm.test.TextKeyCollectionPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

public class PersonTest extends TextKeyCollectionPersistenceTests<Person> {

	@Override
	protected PersonManager getChildObjectManager() {
		return model.getPersons();
	}

	@Override
	protected void doSetupBeforeTransaction() {
		super.doSetupBeforeTransaction();
		Customer customer = model.getCustomers().create("testCustomer");
		Folder folderA = customer.getFolders().create("testFolderA");
		Group groupA = customer.getGroups().create("testGroupA");
		
		Person person = model.getPersons().create("testPerson");
		
		folderA.getGroups().create(groupA);
		
		CustomerPerson personCustomer = person.getPersonCustomers().create(customer);
		personCustomer.getCustomerPersonGroups().create(groupA);
	}
	
	@Test
	public void test() {
		Customer customer = model.customers("testCustomer");
		Folder folderA = customer.folders("testFolderA");
		Group groupA = customer.groups("testGroupA");
		Person person = model.persons("testPerson");

		CustomerPerson cp = person.getPersonCustomers().getAssociationWith(customer);
		
		assertFalse(folderA.getGroups().isEmpty());
		assertFalse(groupA.getGroupFolders().isEmpty());
		
		assertTrue(folderA.getGroups().hasAssociate(groupA));
		assertTrue(groupA.getGroupFolders().hasAssociate(folderA));
		
		// User is in group A
		assertTrue(cp.getCustomerPersonGroups().hasAssociate(groupA));
		
		// User is in a group that has access to folder A 
		assertTrue(
				cp
				.getCustomerPersonGroups()
				.getAssociationWith(groupA)
				.getGroup()
				.getGroupFolders()
				.hasAssociate(folderA)
		);
		
		
		// Get all the user's folders
		Set<Folder> folders = 
		cp.getCustomerPersonGroups().stream().map( 
			cpg -> cpg.getGroup().getGroupFolders().getAssociates()
		)
		.reduce(new HashSet<Folder>(), (a, b) -> { a.addAll(b); return a; } );
		
		// And then see if the set contains folder A
		assertTrue(folders.contains(folderA));
		
		// Or the old-fashioned way
		Set<Folder> yeOldeFolders = new HashSet<Folder>();
		for (CustomerPersonGroup cpg: cp.getCustomerPersonGroups()) {
			for (Folder f: cpg.getGroup().getGroupFolders().getAssociates()) {
				yeOldeFolders.add(f);
			}
		}
		assertTrue(yeOldeFolders.contains(folderA));
	}

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers("testCustomer"));
		model.getPersons().remove(model.persons("testPerson"));
		super.doTeardownAfterTransaction();
	}
	
	

}
