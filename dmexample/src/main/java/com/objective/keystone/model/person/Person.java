package com.objective.keystone.model.person;

import java.util.HashSet;
import java.util.List;
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
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.PersonCustomerManager;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

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
