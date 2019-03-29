package com.objective.keystone.model.group;

import java.util.Set;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.customer.Customer;

public class GroupManager extends DomainObjectCollectionManager<Group> {
	
	private Customer customer;
	
	public GroupManager(Customer customer, Set<Group> g) {
		super(g);
		this.customer = customer;
	}

	@Override
	protected Group instantiate(String name) {
		return new Group(customer, GroupType.create, name, name);
	}

	@Override
	public Class<Group> getManagedObjectClass() {
		return Group.class;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	

}
