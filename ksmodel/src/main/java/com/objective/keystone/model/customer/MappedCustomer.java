package com.objective.keystone.model.customer;

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

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.objective.keystone.model.domain.Domain;
import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.customer.CustomerPerson;

import au.id.lagod.dm.XmlMap;
import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;

@MappedSuperclass
public class MappedCustomer extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", updatable = false, nullable = false)	protected Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="customer_type", length = 10 )
	@NotNull															protected CustomerType type;
	
	@Column(name="customer_name", length = 255)
	@NotBlank @Size(max=255)											protected String name;
	
	@TextKey
	@Column(name="customer_identifier", length = 20)
	@NotBlank @Size(max=20)												protected String identifier;
	
	@Column(name="customer_uuid")
	@NotBlank															protected String uuid;
	
	@Column(name="customer_metadata")
	@Type(type="com.objective.keystone.persistence.MetadataMapBasicType")
																		protected XmlMap metadata = new XmlMap();

	@Size(max=2)
	@Column(name="customer_language")									protected String language;
	@Size(max=2)
	@Column(name="customer_country")									protected String country;

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<CustomerPerson> customerPersonSet = new HashSet<CustomerPerson>();

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Group> groupSet = new HashSet<Group>();

	@OneToMany(cascade = CascadeType.ALL, targetEntity=Folder.class,
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Folder> folderSet = new HashSet<Folder>();

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Event> eventSet = new HashSet<Event>();

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Domain> domainSet = new HashSet<Domain>();

	protected MappedCustomer() {}
	
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

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}
	
	public XmlMap getMetadata() {
		return (XmlMap) metadata.get("metadata");
	}

	
}
