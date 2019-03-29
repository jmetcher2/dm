package com.objective.keystone.model.event;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.ChildDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.ConsultFolder;
import com.sun.istack.NotNull;

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
		this.folder = customer.getConsultFolders().getRoot();
	}

}
