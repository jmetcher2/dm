package com.objective.keystone.model.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.objective.keystone.model.customer.Customer;

@Entity
@Table(name="publisher_domain")
public class Domain extends MappedDomain {

	public Domain() {}
	
	protected Domain(Customer customer, DomainType type, String url) {
		this.customer = customer;
		this.type = type;
		this.url = url;
		this.language = customer.getLocale();
	}

	public void setType(DomainType type) {
		this.type = type;	
	}
}
