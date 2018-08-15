package com.objective.keystone.model.customer;

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

import com.objective.keystone.model.person.CustomerPerson;
import com.objective.keystone.model.person.CustomerPersonManager;
import com.objective.keystone.model.person.PersonCustomerManager;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;

@Entity
@Table(name="publisher_customer")
public class Customer extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", updatable = false, nullable = false)	Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="customer_type", length = 10 )
	@NotNull															CustomerType type;
	
	@Column(name="customer_name", length = 255)
	@NotBlank @Size(max=255)											String name;
	
	@TextKey
	@Column(name="customer_identifier", length = 20)
	@NotBlank @Size(max=20)												String identifier;
	
	@Column(name="customer_uuid")
	@NotBlank															String uuid;

	@AttributeAccessor("com.objective.dm.persistence.collectiongetter.PropertyAccessStrategyCollectionImpl")
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				Set<CustomerPerson> customerPersons = new CustomerPersonManager(this);

	protected Customer() {}
	
	protected Customer(CustomerType type, String name, String identifier, String uuid) {
		super();
		this.type = type;
		this.name = name;
		this.identifier = identifier;
		this.uuid = uuid;
	}
	
	public Long getId() {
		return id;
	}

	public CustomerType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getUuid() {
		return uuid;
	}

	public CustomerPersonManager getCustomerPersons() {
		return (CustomerPersonManager) customerPersons;
	}
	
	
	
	

}
