package com.objective.keystone.model.group;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.validator.constraints.NotBlank;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.ChildDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.GroupFolder;
import com.objective.keystone.model.group.folder.GroupFolderManager;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;
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
