package com.objective.keystone.model.group.folder;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

public class GroupFolderManager extends AssociationCollectionManager<Group, GroupFolder, Folder> {

	public GroupFolderManager(Group parent) {
		super(parent);
	}

	@Override
	public String getAssociateName() {
		return "folder";
	}

	@Override
	protected Class<Folder> getAssociateClass() {
		return Folder.class;
	}

	@Override
	protected GroupFolder newAssociationObject(BaseDomainObject associate) {
		return new GroupFolder(parent, (Folder) associate);
	}

	@Override
	public Class<GroupFolder> getManagedObjectClass() {
		return GroupFolder.class;
	}

}
