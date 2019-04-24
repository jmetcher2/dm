package com.objective.keystone.model.folder;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DiscriminatorFormula;

import au.id.lagod.dm.base.ChildDomainObject;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.FolderGroupManager;

@Entity
@Table(name="publisher_folder")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  
@DiscriminatorFormula("case when folder_type in ('consult', 'consult_root') then 'consult' else 'folder' end")
public abstract class Folder extends MappedFolder implements ChildDomainObject {

	protected Folder() {}
	
	protected Folder(Customer customer, FolderType type, String name, String identifier) {
		super();
		this.type = type.name();
		this.name = name;
		this.shortName = name;
		this.eTag = UUID.randomUUID().toString();
		this.customer = customer;
	}
	

	public abstract Boolean isAuthoringFolder();
	public abstract Boolean isConsultFolder();
	public abstract Boolean isRoot();

	public FolderGroupManager getGroups() {
		return new FolderGroupManager(this, groupSet);
	}

}
