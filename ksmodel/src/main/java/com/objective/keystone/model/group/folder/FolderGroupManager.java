package com.objective.keystone.model.group.folder;

import java.util.Collection;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.BaseDomainObject;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

public class FolderGroupManager extends AssociationCollectionManager<Folder, GroupFolder, Group> {

	public FolderGroupManager(Folder parent, Collection<GroupFolder> c) {
		super(parent, c);
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

	@Override
	protected Group getAssociate(GroupFolder ao) {
		return ao.getGroup();
	}

}
