package com.objective.keystone.model.folder;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.Parameter;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.ChildDomainObject;
import au.id.lagod.dm.base.TextKey;

@Entity
@Table(name="publisher_folder")
public class Folder extends BaseDomainObject implements ChildDomainObject {

	@Id	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "folder-generator")
	@GenericGenerator(
			name="folder-generator",
			strategy="com.objective.dm.persistence.CustomTableIDGenerator",
			parameters = {
					@Parameter(name="sp_name", value="get_folder_id_nextval")
			}
		)
	@Column(name = "folder_id", updatable = false, nullable = false)	private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="folder_type", length = 12 )
	@NotNull														private FolderType type;
	
	@TextKey
	@Column(name="folder_short_name", length = 255)
	@NotBlank @Size(max=255)										private String shortName;
	
	@Column(name="folder_name", length = 255)
	@NotBlank @Size(max=255)										private String name;
	
	@Column(name="folder_etag")
	@NotBlank														private String eTag;

	@ManyToOne @JoinColumn(name="folder_customer_id")				private Customer customer;
	
	protected Folder() {}
	
	protected Folder(Customer customer, FolderType type, String name, String identifier) {
		super();
		this.type = type;
		this.name = name;
		this.shortName = name;
		this.eTag = UUID.randomUUID().toString();
		this.customer = customer;
	}
	
	public Long getId() {
		return id;
	}

	public FolderType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public String getShortName() {
		return shortName;
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
