package com.objective.keystone.model.person;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.person.customer.CustomerPerson;

@MappedSuperclass
public class MappedPerson extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id", updatable = false, nullable = false)	protected Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="person_type", length = 13 )
	@NotNull															protected PersonType type;
	
	@Column(name="person_family_name", length = 35)
	@NotBlank @Size(max=35)												protected String familyName;
	
	@TextKey
	@Column(name="person_username", length = 100)
	@NotBlank @Size(max=100)											protected String userName;
	
	@Column(name="person_etag")
	@NotBlank															protected String eTag;

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "person", orphanRemoval = true)					protected Set<CustomerPerson> personCustomerSet = new HashSet<CustomerPerson>();
	
	protected MappedPerson() {}
	
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

}
