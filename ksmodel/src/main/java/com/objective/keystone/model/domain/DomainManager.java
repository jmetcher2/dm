package com.objective.keystone.model.domain;

import java.util.Set;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class DomainManager extends DomainObjectCollectionManager<Domain> {

	private Customer customer;
	
	public DomainManager(Customer customer, Set<Domain> e) {
		super(e);
		this.customer = customer;
	}

	@Override
	protected Domain instantiate(String url) {
		return new Domain(customer, DomainType.creation, url);
	}

	@Override
	public Class<Domain> getManagedObjectClass() {
		return Domain.class;
	}

}
