package com.objective.keystone.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.dm.test.AssociationPersistenceTests;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.CustomerPersonManager;

public class CustomerPersonTest extends AssociationPersistenceTests<CustomerPerson, Customer, Person> {
	
	@Override
	protected void doSetupBeforeTransaction(){
		getParent1Manager().create(parent1Name);
		getParent2Manager().create(parent2Name);
		getParent2Manager().create(parent2InTestName);
		super.doSetupBeforeTransaction();
	}
	
	@Override
	protected void doTeardownAfterTransaction(){
		super.doTeardownAfterTransaction();
		getParent2Manager().remove(getParent2InTest());
		getParent2Manager().remove(getParent2());
		getParent1Manager().remove(getParent1());
	}


	@Override
	protected DomainObjectCollectionManager<Customer> getParent1Manager() {
		return model.getCustomers();
	}

	@Override
	protected DomainObjectCollectionManager<Person> getParent2Manager() {
		return model.getPersons();
	}

	@Override
	protected CustomerPersonManager getChildObjectManager() {
		return getParent1().getCustomerPersons();
	}

	@Override
	public Customer getParent1() {
		return getParent1Manager().get(parent1Name);
	}

	@Override
	protected Person getParent2() {
		return getParent2Manager().get(parent2Name);
	}

	@Override
	protected Person getParent2InTest() {
		return getParent2Manager().get(parent2InTestName);
	}

	@Override
	protected String getFindKey() {
		return "person." + Person.getTextKeyField(Person.class);
	}

	@Override
	protected Object getFindValue() {
		return getParent2().getTextKey();
	}
	
	@Override
	@Test
	public void testGetAttached() {
		sf.getCurrentSession().update(domainObject);
		sf.getCurrentSession().update(((CustomerPerson) domainObject).getPerson());
		
		CustomerPerson domainObject2 = getChildObject();
		
		assertEquals(domainObject.getId(), domainObject2.getId());
		assertEquals(domainObject, domainObject2);

		checkCreatedObject(domainObject2);
	}





}
