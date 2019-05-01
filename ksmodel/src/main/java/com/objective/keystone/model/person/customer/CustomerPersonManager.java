package com.objective.keystone.model.person.customer;

import java.util.Set;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.BaseDomainObject;

public class CustomerPersonManager extends AssociationCollectionManager<Customer, CustomerPerson, Person> {

	public CustomerPersonManager(Customer parent, Set<CustomerPerson> cp) {
		super(parent, cp);
	}

	@Override
	public String getAssociateName() {
		return "person";
	}
	
	@Override
	protected CustomerPerson newAssociationObject(BaseDomainObject associate) {
		return new CustomerPerson(CustomerPersonType.user, parent, (Person) associate);
	}

	@Override
	public Class<CustomerPerson> getManagedObjectClass() {
		return CustomerPerson.class;
	}

	@Override
	protected Class<Person> getAssociateClass() {
		return Person.class;
	}

	@Override
	protected Person getAssociate(CustomerPerson ao) {
		return ao.getPerson();
	}

}
