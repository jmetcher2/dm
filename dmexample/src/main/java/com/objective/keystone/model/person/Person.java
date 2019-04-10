package com.objective.keystone.model.person;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.objective.keystone.model.person.customer.PersonCustomerManager;

@Entity
@Table(name="publisher_person")
public class Person extends MappedPerson {

	@Transient private PersonCustomerManager personCustomers = new PersonCustomerManager(this, personCustomerSet);
	
	protected Person() {}
	
	protected Person(PersonType type, String familyName, String userName, String eTag) {
		super();
		this.type = type;
		this.familyName = familyName;
		this.userName = userName;
		this.eTag = eTag;
	}
	
	public PersonCustomerManager getPersonCustomers() {
		return personCustomers;
	}

}
