package com.objective.keystone.model.person;

import java.util.HashSet;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

public class PersonCustomerManager extends AssociationCollectionManager<Customer, CustomerPerson, Person> {

	private Person person;

	public PersonCustomerManager(Person person) {
		super(new HashSet<CustomerPerson>());
		this.person = person;
	}
	
	@Override
	public String getAssociateName() {
		return "person";
	}

	@Override
	protected CustomerPerson newAssociationObject(BaseDomainObject associate) {
		Customer customer = (Customer) associate;
		CustomerPerson cp = new CustomerPerson(CustomerPersonType.user, customer, person);
		return cp;
	}
	
	@Override
	public Class<CustomerPerson> getManagedObjectClass() {
		return CustomerPerson.class;
	}

}
