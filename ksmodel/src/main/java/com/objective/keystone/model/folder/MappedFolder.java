package com.objective.keystone.model.folder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.GroupFolder;

@MappedSuperclass
public class MappedFolder extends BaseDomainObject{

	@Id	
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "folder-generator")
	@GenericGenerator(
			name="folder-generator",
			strategy="au.id.lagod.dm.persistence.CustomTableIDGenerator",
			parameters = {
					@Parameter(name="sp_name", value="get_folder_id_nextval")
			}
		)
	@Column(name = "folder_id", updatable = false, nullable = false)	protected Long id;
	
	//@Convert(converter=FolderTypeConverter.class)
    @Column(name="folder_type", length = 12, insertable=false, updatable=false )
	@NotNull														protected String type;
	
	@TextKey
	@Column(name="folder_short_name", length = 255)
	@NotBlank @Size(max=255)										protected String shortName;
	
	@Column(name="folder_name", length = 255)
	@NotBlank @Size(max=255)										protected String name;
	
	@Column(name="folder_etag")
	@NotBlank														protected String eTag;

	@ManyToOne
	@JoinColumn(name="folder_customer_id")				protected Customer customer;
	
	@OneToMany(mappedBy="folder", cascade=CascadeType.ALL, orphanRemoval = true)
																	protected Set<GroupFolder> groupSet = new HashSet<GroupFolder>();

	protected MappedFolder() {}
	
	public Long getId() {
		return id;
	}

	public String getType() {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eTag == null) ? 0 : eTag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AuthoringFolder other = (AuthoringFolder) obj;
		if (eTag == null) {
			if (other.eTag != null)
				return false;
		} else if (!eTag.equals(other.eTag))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	

}
