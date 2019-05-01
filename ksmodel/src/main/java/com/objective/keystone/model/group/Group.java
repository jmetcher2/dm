package com.objective.keystone.model.group;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.GroupFolderManager;
import com.objective.keystone.model.person.customer.group.GroupCustomerPersonManager;

import au.id.lagod.dm.base.ChildDomainObject;

@Entity
@Table(name="publisher_group")
public class Group extends MappedGroup implements ChildDomainObject {

	protected Group() {}
	
	protected Group(Customer customer, GroupType type, String name, String identifier) {
		super();
		this.type = type;
		this.name = name;
		this.eTag = UUID.randomUUID().toString();
		this.customer = customer;
	}
	
	public Long getId() {
		return id;
	}

	public GroupType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getETag() {
		return eTag;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public Customer getParent() {
		return getCustomer();
	}
	
	public GroupFolderManager getGroupFolders() {
		return new GroupFolderManager(this, folderSet);
	}

	public GroupCustomerPersonManager getCustomerPersonGroups() {
		return new GroupCustomerPersonManager(this, customerPersonSet);
	}


}
