package com.objective.keystone.model.folder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.objective.keystone.model.customer.Customer;

@Entity
@DiscriminatorValue("folder")
public class AuthoringFolder extends Folder {

	public static final String ROOT_NAME = "Root";
	
	protected AuthoringFolder() {}
	
	protected AuthoringFolder(Customer customer, String name, String identifier, Boolean setRoot) {
		super(customer, FolderType.folder, name, identifier);
		if (setRoot) 
			this.type = FolderType.root.name();
	}
	
	public Boolean isRoot() {
		return getType().equals(FolderType.root.name());
	}

	@Override
	public Boolean isAuthoringFolder() {
		return true;
	}

	@Override
	public Boolean isConsultFolder() {
		return false;
	}

}
