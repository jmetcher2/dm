package com.objective.keystone.model.person;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.customer.Customer;

public class CustomerPersonManager extends AssociationCollectionManager<Customer, CustomerPerson, Person> {

	public CustomerPersonManager(Customer parent) {
		super(parent);
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

}
