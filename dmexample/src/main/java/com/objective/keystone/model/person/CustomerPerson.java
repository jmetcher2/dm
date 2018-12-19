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

import com.objective.dm.base.AssociationParents;
import com.objective.dm.base.BaseAssociationDomainObject;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

@Entity
@Table(name="publisher_customer_person_lnk")
public class CustomerPerson extends BaseAssociationDomainObject<Customer, Person>  {

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
	public AssociationParents<Customer, CustomerPerson, Person> getAssociationParents() {
		return new AssociationParents<Customer, CustomerPerson, Person>(customer.getCustomerPersons(), person.getPersonCustomers());
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
		CustomerPerson other = (CustomerPerson) obj;
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
