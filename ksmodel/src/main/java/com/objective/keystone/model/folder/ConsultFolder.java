package com.objective.keystone.model.folder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.objective.keystone.model.customer.Customer;

@Entity
@DiscriminatorValue("consult")
public class ConsultFolder extends Folder {

	public static final String CONSULT_ROOT_NAME = "Consultation home";
	
	protected ConsultFolder() {}

	protected ConsultFolder(Customer customer, String name, String identifier, Boolean setRoot) {
		super(customer, FolderType.consult, name, identifier);
		if (setRoot) 
			this.type = FolderType.consult_root.name();
	}
	
	public Boolean isRoot() {
		return getType().equals(FolderType.consult_root.name());
	}

	@Override
	public Boolean isAuthoringFolder() {
		return false;
	}

	@Override
	public Boolean isConsultFolder() {
		return false;
	}

}
