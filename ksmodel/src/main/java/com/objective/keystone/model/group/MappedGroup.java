package com.objective.keystone.model.group;

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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.ChildDomainObject;
import au.id.lagod.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.GroupFolder;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

@MappedSuperclass
public class MappedGroup extends BaseDomainObject implements ChildDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", updatable = false, nullable = false)	protected Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="group_type", length = 10 )
	@NotNull														protected GroupType type;
	
	@TextKey
	@Column(name="group_name", length = 255)
	@NotBlank @Size(max=255)										protected String name;
	
	@Column(name="group_etag")
	@NotBlank														protected String eTag;

	@ManyToOne @JoinColumn(name="group_customer_id")				protected Customer customer;
	
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
																	protected Set<GroupFolder> folderSet = new HashSet<GroupFolder>();
	
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
    																protected Set<CustomerPersonGroup> customerPersonSet = new HashSet<CustomerPersonGroup>();


	protected MappedGroup() {}
	
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
