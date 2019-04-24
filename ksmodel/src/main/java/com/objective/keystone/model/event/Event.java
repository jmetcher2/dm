package com.objective.keystone.model.event;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import au.id.lagod.dm.base.ChildDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.ConsultFolder;

@Entity
@Table(name="publisher_event")
public class Event extends MappedEvent implements ChildDomainObject {

	protected Event() {}
	
	protected Event(Customer customer, EventType type, String name) {
		super();
		this.customer = customer;
		this.type = type;
		this.name = name;
		this.shortName = name;
		this.eTag = UUID.randomUUID().toString();
		this.folder = (ConsultFolder) customer.getFolders().getConsultRoot();
	}

}
