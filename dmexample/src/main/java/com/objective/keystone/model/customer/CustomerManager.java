package com.objective.keystone.model.customer;

import java.util.UUID;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class CustomerManager extends DomainObjectCollectionManager<Customer> {

	@Override
	public Customer create(String name) {
		Customer u = new Customer(CustomerType.both, name, name, UUID.randomUUID().toString());
		add(u);
		return u;
	}

	@Override
	public Class<Customer> getManagedObjectClass() {
		return Customer.class;
	}
	

}
