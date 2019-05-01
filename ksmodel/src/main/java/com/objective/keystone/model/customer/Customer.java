package com.objective.keystone.model.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.objective.keystone.model.domain.DomainManager;
import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.event.EventManager;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.group.GroupManager;
import com.objective.keystone.model.person.customer.CustomerPersonManager;

import au.id.lagod.dm.XmlMap;

@Entity
@Table(name="publisher_customer")
public class Customer extends MappedCustomer {

	protected Customer() {}
	
	protected Customer(CustomerType type, String name, String identifier, String uuid, String language, String country) {
		super();
		this.type = type;
		this.name = name;
		this.identifier = identifier;
		this.uuid = uuid;
		this.language = language;
		this.country = country;
		this.metadata.put("metadata", new XmlMap());
		
		getFolders().createRoot();
		getFolders().createConsultRoot();
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	
	public void setLanguage(String lan) {
		this.language = lan;
	}
	
	public String getLocale() {
		return getLanguage() + "_" + getCountry();
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
		
	public EventManager getEvents() {
		return new EventManager(this, eventSet);
	}
	
	public Event events(String name) {
		return getEvents().get(name);
	}
	
	public DomainManager getDomains() {
		return new DomainManager(this, domainSet);
	}

}
