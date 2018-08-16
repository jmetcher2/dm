package com.objective.keystone.model.group;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.ChildDomainObject;
import au.id.lagod.dm.base.TextKey;

@Entity
@Table(name="publisher_group")
public class Group extends BaseDomainObject implements ChildDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", updatable = false, nullable = false)	private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="group_type", length = 10 )
	@NotNull														private GroupType type;
	
	@TextKey
	@Column(name="group_name", length = 255)
	@NotBlank @Size(max=255)										private String name;
	
	@Column(name="group_etag")
	@NotBlank														private String eTag;

	@ManyToOne @JoinColumn(name="group_customer_id")				private Customer customer;

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


}
