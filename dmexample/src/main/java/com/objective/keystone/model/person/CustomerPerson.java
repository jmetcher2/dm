package com.objective.keystone.model.person;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.BaseDomainObject;

@Entity
@Table(name="publisher_customer_person_lnk")
public class CustomerPerson extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id", updatable = false, nullable = false)	Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="link_type", length = 10 )
	@NotNull														CustomerPersonType type;
	
	@Column(name="person_etag")
	@NotBlank														String eTag;

	@ManyToOne 														Customer customer;
	@ManyToOne														Person person;
	
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
