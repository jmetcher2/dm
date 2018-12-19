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

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.group.GroupManager;
import com.objective.keystone.model.person.CustomerPerson;
import com.objective.keystone.model.person.CustomerPersonManager;
import com.objective.keystone.model.person.PersonCustomerManager;

@Entity
@Table(name="publisher_customer")
public class Customer extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id", updatable = false, nullable = false)	private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="customer_type", length = 10 )
	@NotNull															private CustomerType type;
	
	@Column(name="customer_name", length = 255)
	@NotBlank @Size(max=255)											private String name;
	
	@TextKey
	@Column(name="customer_identifier", length = 20)
	@NotBlank @Size(max=20)												private String identifier;
	
	@Column(name="customer_uuid")
	@NotBlank															private String uuid;

	@AttributeAccessor(collectionAccessor)
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				private Set<CustomerPerson> customerPersons = new CustomerPersonManager(this);

	@AttributeAccessor(collectionAccessor)
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				private Set<Group> groups = new GroupManager(this);

	@AttributeAccessor(collectionAccessor)
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				private Set<Folder> folders = new FolderManager(this);


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
	
	public GroupManager getGroups() {
		return (GroupManager) groups;
	}
	
	public Group groups(String name) {
		return getGroups().get(name);
	}
	
	public FolderManager getFolders() {
		return (FolderManager) folders;
	}
	
	public Folder folders(String name) {
		return getFolders().get(name);
	}
		
	
	

}
