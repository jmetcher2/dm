package com.objective.keystone.model.group.folder;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

public class FolderGroupManager extends AssociationCollectionManager<Folder, GroupFolder, Group> {

	public FolderGroupManager(Folder parent) {
		super(parent);
	}

	@Override
	public String getAssociateName() {
		return "group";
	}

	@Override
	protected Class<Group> getAssociateClass() {
		return Group.class;
	}

	@Override
	protected GroupFolder newAssociationObject(BaseDomainObject associate) {
		return new GroupFolder((Group) associate, parent);
	}

	@Override
	public Class<GroupFolder> getManagedObjectClass() {
		return GroupFolder.class;
	}

}
