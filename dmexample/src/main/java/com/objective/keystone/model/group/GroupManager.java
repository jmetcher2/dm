package com.objective.keystone.model.group;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class GroupManager extends DomainObjectCollectionManager<Group> {
	
	private Customer customer;
	
	public GroupManager(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public Group create(String name) {
		Group u = new Group(customer, GroupType.create, name, name);
		add(u);
		return u;
	}

	@Override
	public Class<Group> getManagedObjectClass() {
		return Group.class;
	}
	

}
