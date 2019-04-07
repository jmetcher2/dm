package com.objective.keystone.model.customer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.validator.constraints.NotBlank;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.event.EventManager;
import com.objective.keystone.model.folder.AbstractFolder;
import com.objective.keystone.model.folder.ConsultFolder;
import com.objective.keystone.model.folder.ConsultFolderManager;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.group.GroupManager;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.CustomerPersonManager;

@Entity
@Table(name="publisher_customer")
public class Customer extends MappedCustomer {

	protected Customer() {}
	
	protected Customer(CustomerType type, String name, String identifier, String uuid) {
		super();
		this.type = type;
		this.name = name;
		this.identifier = identifier;
		this.uuid = uuid;
		
		getConsultFolders().createRoot();
		getFolders().createRoot();
	}
	
	public Long getId() {
		return id;
	}

	public CustomerType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getUuid() {
		return uuid;
	}

	public CustomerPersonManager getCustomerPersons() {
		return new CustomerPersonManager(this, customerPersonSet);
	}
	
	public GroupManager getGroups() {
		return new GroupManager(this, groupSet);
	}
	
	public Group groups(String name) {
		return getGroups().get(name);
	}
	
	public FolderManager getFolders() {
		return new FolderManager(this, folderSet);
	}
	
	public Folder folders(String name) {
		return getFolders().get(name);
	}
		
	public ConsultFolderManager getConsultFolders() {
		return new ConsultFolderManager(this, consultFolderSet);
	}
	
	public ConsultFolder consultFolders(String name) {
		return getConsultFolders().get(name);
	}
		
	public EventManager getEvents() {
		return new EventManager(this, eventSet);
	}
	
	public Event events(String name) {
		return getEvents().get(name);
	}
	

}
