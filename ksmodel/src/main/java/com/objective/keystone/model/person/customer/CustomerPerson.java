package com.objective.keystone.model.person.customer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroupManager;

import au.id.lagod.dm.base.AssociationParents;

@Entity
@Table(name="publisher_customer_person_lnk")
public class CustomerPerson extends MappedCustomerPerson  {

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
		return new CustomerPersonGroupManager(this, groupSet);
	}
	
	

}
