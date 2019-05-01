package com.objective.keystone.model.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;

@MappedSuperclass
public class MappedDomain  extends BaseDomainObject{
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="domain_id")					protected Long id;

	@TextKey @Size(max=255)
	@Column(name="domain_url")					protected String url;
	
	@Column(name="domain_created_by")			protected Long createdBy; 
	@Column(name="domain_created_on")			protected LocalDateTime createdOn; 

	@ManyToOne 
	@JoinColumn(name="domain_customer_id")		protected Customer customer;
	
	@Column(name="domain_default_domain")		protected Boolean defaultDomain; 
	@Column(name="domain_metadata")				protected String metadata; 
	@Column(name="domain_modified_by")			protected Long modifiedBy; 
	@Column(name="domain_modified_on")			protected LocalDateTime modifiedOn; 
	
	@Enumerated(EnumType.STRING)
	@Column(name="domain_type")					protected DomainType type; 
	
	@NotNull
	@Size(max=5)
	@Column(name="domain_language")				protected String language;
	
	
	public Long getId() {
		return id;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Boolean getDefaultDomain() {
		return defaultDomain;
	}
	public String getMetadata() {
		return metadata;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public DomainType getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public String getLanguage() {
		return language;
	}
	
	
}
