package com.objective.keystone.model.person;

import java.util.HashSet;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class CustomerPersonManager extends AssociationCollectionManager<Customer, CustomerPerson, Person> {

	private Customer customer;

	public CustomerPersonManager(Customer customer) {
		super(new HashSet<CustomerPerson>());
		this.customer = customer;
	}
	
	@Override
	protected Person getAssociate(CustomerPerson ao) {
		return ao.getPerson();
	}

	@Override
	protected DomainObjectCollectionManager<Person> getAssociateMasterCollection() {
		return Model.getModel().getPersons();
	}

	@Override
	protected String getAssociateName() {
		return "person";
	}

	@Override
	protected AssociationCollectionManager<Person, CustomerPerson, Customer> getReverseCollection(
			CustomerPerson associationObject) {
		return associationObject.getPerson().getPersonCustomers();
	}

	@Override
	protected CustomerPerson newAssociationObject(Person associate) {
		return new CustomerPerson(CustomerPersonType.user, customer, associate);
	}

	@Override
	public Class<CustomerPerson> getManagedObjectClass() {
		return CustomerPerson.class;
	}

}
