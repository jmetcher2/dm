package com.objective.keystone.model;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.CustomerPersonManager;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.AssociationPersistenceTests;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class CustomerPersonGroupTest extends AssociationPersistenceTests<CustomerPersonGroup, CustomerPerson, Group> {
	@Autowired
	protected Model model;

	private static final String testCustomerName = "testCustomer";
	
	private Customer getTestCustomer() {
		return model.customers(testCustomerName);
	}
	
	@Override
	protected void doSetupBeforeTransaction(){
		model.getCustomers().create(testCustomerName);
		Person person = model.getPersons().create(parent1Name);
		getParent1Manager().create(person);
		getParent2Manager().create(parent2Name);
		getParent2Manager().create(parent2InTestName);
		super.doSetupBeforeTransaction();
	}
	
	@Override
	protected void doTeardownAfterTransaction(){
		super.doTeardownAfterTransaction();
		model.getCustomers().remove(getTestCustomer());
		model.getPersons().remove(model.persons(parent1Name));
	}


	
	@Override
	protected AssociationCollectionManager<CustomerPerson, CustomerPersonGroup, Group> getChildObjectManager() {
		return getParent1().getCustomerPersonGroups();
	}

	@Override
	protected  CustomerPersonManager getParent1Manager() {
		return getTestCustomer().getCustomerPersons();
	}

	@Override
	protected DomainObjectCollectionManager<Group> getParent2Manager() {
		return getTestCustomer().getGroups();
	}

	@Override
	protected CustomerPerson getParent1() {
		return getParent1Manager().getAssociationWith(model.persons(parent1Name));
	}

	@Override
	protected Group getParent2() {
		return getParent2Manager().get(parent2Name);
	}

	@Override
	protected Group getParent2InTest() {
		return getParent2Manager().get(parent2InTestName);
	}

	@Override
	protected String getFindKey() {
		return "group." + Group.getTextKeyField(Group.class);
	}

	@Override
	protected Object getFindValue() {
		return parent2Name;
	}
	
	@Override
	@Ignore
	@Test
	public void testGetAttached() {
	}
	

}
