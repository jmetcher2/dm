package com.objective.keystone.model.customer;

import java.util.UUID;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class CustomerManager extends DomainObjectCollectionManager<Customer> {
	public final static String defaultLanguage = "en";
	public final static String defaultCountry = "GB";
	
	@Override
	protected Customer instantiate(String name) {
		return new Customer(CustomerType.both, name, name, UUID.randomUUID().toString(), defaultLanguage, defaultCountry);
	}

	@Override
	public Class<Customer> getManagedObjectClass() {
		return Customer.class;
	}
	
//	@Override
//	public boolean remove(Object o) {
//		return super.remove(o);
//	}
	
	public void removeAssociates(Customer c) {
		
	}
	

}
