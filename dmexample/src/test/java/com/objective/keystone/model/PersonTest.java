package com.objective.keystone.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		CustomerPersonGroup cpg = personCustomer.getGroups().create(groupA);
		
		System.out.println(folderA.getGroups().isEmpty());
		System.out.println(groupA.getFolders().isEmpty());
	}
	
	@Test
	public void test() {
		Customer customer = model.customers("testCustomer");
		Folder folderA = customer.folders("testFolderA");
		Group groupA = customer.groups("testGroupA");
		Person person = model.persons("testPerson");
		
		assertFalse(folderA.getGroups().isEmpty());
		assertFalse(groupA.getFolders().isEmpty());
		
		assertTrue(folderA.getGroups().hasAssociate(groupA));
		assertTrue(groupA.getFolders().hasAssociate(folderA));
		
		// User is in group A
		assertTrue(person.getPersonCustomers().get(customer).getGroups().hasAssociate(groupA));
		
		// User is in a group that has access to folder A 
		assertTrue(person.getPersonCustomers().get(customer).getGroups().get(groupA).getGroup().getFolders().hasAssociate(folderA));
	}

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers("testCustomer"));
		model.getPersons().remove(model.persons("testPerson"));
		super.doTeardownAfterTransaction();
	}
	
	

}
