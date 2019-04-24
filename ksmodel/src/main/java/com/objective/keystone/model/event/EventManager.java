package com.objective.keystone.model.event;

import java.util.Set;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.customer.Customer;

public class EventManager extends DomainObjectCollectionManager<Event> {
	
	private Customer customer;
	
	public EventManager(Customer customer, Set<Event> e) {
		super(e);
		this.customer = customer;
	}

	@Override
	protected Event instantiate(String name) {
		return new Event(customer, EventType.live_document, name);
	}

	@Override
	public Class<Event> getManagedObjectClass() {
		return Event.class;
	}

	public Customer getCustomer() {
		return customer;
	}
	

}
