package com.objective.keystone.model.folder;

import java.util.Set;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.AttributeAccessor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotBlank;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.ChildDomainObject;
import com.objective.dm.base.TextKey;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.group.folder.FolderGroupManager;
import com.objective.keystone.model.group.folder.GroupFolder;
import com.objective.keystone.persistence.FolderTypeConverter;

@Entity
@DiscriminatorValue("consult")
public class ConsultFolder extends AbstractFolder {

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

}
