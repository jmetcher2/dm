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
@DiscriminatorValue("folder")
public class Folder extends AbstractFolder {

	public static final String ROOT_NAME = "Root";
	
	@Transient private FolderGroupManager groups = new FolderGroupManager(this, groupSet);
	
	protected Folder() {}
	
	protected Folder(Customer customer, String name, String identifier, Boolean setRoot) {
		super(customer, FolderType.folder, name, identifier);
		if (setRoot) 
			this.type = FolderType.root.name();
	}
	
	public Boolean isRoot() {
		return getType().equals(FolderType.root.name());
	}
	public FolderGroupManager getGroups() {
		return (FolderGroupManager) groups;
	}

}
