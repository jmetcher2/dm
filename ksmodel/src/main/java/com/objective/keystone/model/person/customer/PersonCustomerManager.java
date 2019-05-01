package com.objective.keystone.model.person.customer;

import java.util.Collection;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.BaseDomainObject;

public class PersonCustomerManager extends AssociationCollectionManager<Person, CustomerPerson, Customer> {

	public PersonCustomerManager(Person person, Collection<CustomerPerson> c) {
		super(person, c);
	}
	
	@Override
	public String getAssociateName() {
		return "customer";
	}

	@Override
	public Class<Customer> getAssociateClass() {
		return Customer.class;
	}
	
	@Override
	protected CustomerPerson newAssociationObject(BaseDomainObject associate) {
		Customer customer = (Customer) associate;
		CustomerPerson cp = new CustomerPerson(CustomerPersonType.user, customer, parent);
		return cp;
	}
	
	@Override
	public Class<CustomerPerson> getManagedObjectClass() {
		return CustomerPerson.class;
	}

	@Override
	protected Customer getAssociate(CustomerPerson ao) {
		return ao.getCustomer();
	}

}
