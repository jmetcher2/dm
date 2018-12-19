package com.objective.keystone.model.person;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.customer.Customer;

public class PersonCustomerManager extends AssociationCollectionManager<Person, CustomerPerson, Customer> {

	public PersonCustomerManager(Person person) {
		super(person);
	}
	
	@Override
	public String getAssociateName() {
		return "person";
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

}
