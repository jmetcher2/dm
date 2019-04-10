package com.objective.keystone.model.group.folder;

import java.util.Collection;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.folder.AuthoringFolder;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

public class GroupFolderManager extends AssociationCollectionManager<Group, GroupFolder, Folder> {

	public GroupFolderManager(Group parent, Collection<GroupFolder> c) {
		super(parent, c);
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
		return new GroupFolder(parent, (AuthoringFolder) associate);
	}

	@Override
	public Class<GroupFolder> getManagedObjectClass() {
		return GroupFolder.class;
	}

	@Override
	protected Folder getAssociate(GroupFolder ao) {
		return ao.getFolder();
	}
	
	// TODO: validation should check that we're associating with an authoring folder

}
