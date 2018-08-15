package com.objective.keystone.model.person;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;

@Entity
@Table(name="publisher_person")
public class Person extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id", updatable = false, nullable = false)	Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="person_type", length = 13 )
	@NotNull															PersonType type;
	
	@Column(name="person_family_name", length = 35)
	@NotBlank @Size(max=35)												String familyName;
	
	@TextKey
	@Column(name="person_username", length = 100)
	@NotBlank @Size(max=100)											String userName;
	
	@Column(name="person_etag")
	@NotBlank															String eTag;

	@AttributeAccessor("com.objective.dm.persistence.collectiongetter.PropertyAccessStrategyCollectionImpl")
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "person", orphanRemoval = true)					Set<CustomerPerson> personCustomers = new PersonCustomerManager(this);
	
	protected Person() {}
	
	protected Person(PersonType type, String familyName, String userName, String eTag) {
		super();
		this.type = type;
		this.familyName = familyName;
		this.userName = userName;
		this.eTag = eTag;
	}
	
	public Long getId() {
		return id;
	}

	public PersonType getType() {
		return type;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getUserName() {
		return userName;
	}

	public String geteTag() {
		return eTag;
	}

	public PersonCustomerManager getPersonCustomers() {
		return (PersonCustomerManager) personCustomers;
	}
	
	
	

}