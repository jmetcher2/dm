package com.objective.keystone.model.person;

import java.util.HashSet;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

import au.id.lagod.dm.base.BaseDomainObject;

@Entity
@Table(name="publisher_customer_person_lnk")
public class CustomerPerson extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id", updatable = false, nullable = false)	private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="link_type", length = 10 )
	@NotNull														private CustomerPersonType type;
	
	@Column(name="link_etag")
	@NotBlank														private String eTag;

	@ManyToOne @JoinColumn(name="link_customer_id")					private Customer customer;
	@ManyToOne @JoinColumn(name="link_person_id")					private Person person;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="publisher_group_person_lnk",
    	joinColumns = @JoinColumn(name="link_id"),
    	inverseJoinColumns = @JoinColumn(name="group_id")
    )																private Set<Group> groups = new HashSet<Group>();
	
	protected CustomerPerson() {}
	
	public CustomerPerson(CustomerPersonType type, Customer customer, Person person) {
		super();
		this.type = type;
		this.customer = customer;
		this.person = person;
		this.eTag = UUID.randomUUID().toString();
	}


	@Override
	public Long getId() {
		return id;
	}

	public CustomerPersonType getType() {
		return type;
	}

	public String geteTag() {
		return eTag;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Person getPerson() {
		return person;
	}
	
	

}
