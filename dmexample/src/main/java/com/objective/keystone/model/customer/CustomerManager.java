package com.objective.keystone.model.customer;

import java.util.UUID;

import com.objective.dm.base.DomainObjectCollectionManager;

public class CustomerManager extends DomainObjectCollectionManager<Customer> {

	@Override
	protected Customer instantiate(String name) {
		return new Customer(CustomerType.both, name, name, UUID.randomUUID().toString());
	}

	@Override
	public Class<Customer> getManagedObjectClass() {
		return Customer.class;
	}
	

}
