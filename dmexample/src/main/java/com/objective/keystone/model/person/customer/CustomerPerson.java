package com.objective.keystone.model.person.customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.validator.constraints.NotBlank;

import com.objective.dm.base.AssociationParents;
import com.objective.dm.base.BaseAssociationDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroupManager;

@Entity
@Table(name="publisher_customer_person_lnk")
public class CustomerPerson extends MappedCustomerPerson  {

	@Transient private CustomerPersonGroupManager groups = new CustomerPersonGroupManager(this, groupSet);
	
	protected CustomerPerson() {}
	
	public CustomerPerson(CustomerPersonType type, Customer customer, Person person) {
		super();
		this.type = type;
		this.customer = customer;
		this.person = person;
		this.eTag = UUID.randomUUID().toString();
	}
	
	
	@Override
	public AssociationParents<Customer, CustomerPerson, Person> getAssociationParents() {
		return new AssociationParents<Customer, CustomerPerson, Person>(customer.getCustomerPersons(), person.getPersonCustomers());
	}

	public Set<Folder> getFolders() {
		Set<Folder> folders = new HashSet<Folder>();
		for (CustomerPersonGroup cpg: getCustomerPersonGroups()) {
			for (Folder f: cpg.getGroup().getGroupFolders().getAssociates()) {
				folders.add(f);
			}
		}
		return folders;
	}

	public CustomerPersonGroupManager getCustomerPersonGroups() {
		return groups;
	}
	
	

}
