package com.objective.keystone.model.person.customer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseAssociationDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

@MappedSuperclass
public abstract class MappedCustomerPerson extends BaseAssociationDomainObject<Customer, Person>  {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id", updatable = false, nullable = false)	protected Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="link_type", length = 10 )
	@NotNull														protected CustomerPersonType type;
	
	@Column(name="link_etag")
	@NotBlank														protected String eTag;

	@ManyToOne @JoinColumn(name="link_customer_id")					protected Customer customer;
	@ManyToOne @JoinColumn(name="link_person_id")					protected Person person;
	
	@OneToMany(mappedBy="customerPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    																protected Set<CustomerPersonGroup> groupSet = new HashSet<CustomerPersonGroup>();
	
	protected MappedCustomerPerson() {}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappedCustomerPerson other = (MappedCustomerPerson) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}


}
