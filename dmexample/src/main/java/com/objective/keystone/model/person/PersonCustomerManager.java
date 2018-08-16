package com.objective.keystone.model.person;

import java.util.HashSet;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class PersonCustomerManager extends AssociationCollectionManager<Person, CustomerPerson, Customer> {

	private Person person;

	public PersonCustomerManager(Person person) {
		super(new HashSet<CustomerPerson>());
		this.person = person;
	}
	
	@Override
	protected Customer getAssociate(CustomerPerson ao) {
		return ao.getCustomer();
	}

	@Override
	public DomainObjectCollectionManager<Customer> getAssociateMasterCollection() {
		return Model.getModel().getCustomers();
	}

	@Override
	public String getAssociateName() {
		return "person";
	}

	@Override
	protected AssociationCollectionManager<Customer, CustomerPerson, Person> getReverseCollection(
			CustomerPerson associationObject) {
		return associationObject.getCustomer().getCustomerPersons();
	}

	@Override
	protected CustomerPerson newAssociationObject(Customer associate) {
		return new CustomerPerson(CustomerPersonType.user, associate, person);
	}

	@Override
	public Class<CustomerPerson> getManagedObjectClass() {
		return CustomerPerson.class;
	}

}
