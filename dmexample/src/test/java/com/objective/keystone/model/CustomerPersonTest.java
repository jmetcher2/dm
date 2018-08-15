package com.objective.keystone.model;

import com.objective.dm.test.BaseAssociationPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.CustomerPerson;
import com.objective.keystone.model.person.Person;

import au.id.lagod.dm.base.AssociationManager;

public class CustomerPersonTest extends BaseAssociationPersistenceTests<CustomerPerson, Customer, Person> {
	
	@Override
	protected void doSetupBeforeTransaction(){
		Customer c = model.getCustomers().create("parentObj");
		Person p = model.getPersons().create(parent2Name1);

		domainObject = c.getCustomerPersons().create(p);
	}
	
	@Override
	protected void doTeardownAfterTransaction(){
		model.getPersons().remove(model.persons(parent2Name1));
		model.getCustomers().remove(getParent());
		
	}



	@Override
	protected AssociationManager<CustomerPerson, Person> getChildObjectManager() {
		return getParent().getCustomerPersons();
	}

	@Override
	protected Person getParent2() {
		return model.persons(parent2Name1);
	}

	@Override
	protected void removeParent() {
		model.getCustomers().remove(getParent());
		
	}

	@Override
	public Customer getParent() {
		return model.customers(testObjectName);
	}

}
