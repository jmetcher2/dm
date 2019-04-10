package com.objective.keystone.model.group;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.objective.dm.base.ChildDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.GroupFolderManager;
import com.objective.keystone.model.person.customer.group.GroupCustomerPersonManager;

@Entity
@Table(name="publisher_group")
public class Group extends MappedGroup implements ChildDomainObject {

	@Transient private GroupFolderManager folders = new GroupFolderManager(this, folderSet);
    @Transient private GroupCustomerPersonManager customerPersons = new GroupCustomerPersonManager(this, customerPersonSet);


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
		return (GroupFolderManager) folders;
	}

	public GroupCustomerPersonManager getCustomerPersonGroups() {
		return (GroupCustomerPersonManager) customerPersons;
	}


}
