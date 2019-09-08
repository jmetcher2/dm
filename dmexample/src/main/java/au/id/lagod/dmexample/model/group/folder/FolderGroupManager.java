package au.id.lagod.dmexample.model.group.folder;

import java.util.Collection;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.group.Group;

public class FolderGroupManager extends AssociationCollectionManager<Folder, GroupFolder, Group> {

	public FolderGroupManager(Folder parent, Collection<GroupFolder> c) {
		super(parent, c, "group",
				Group.class,
				GroupFolder.class);
	}

	@Override
	protected GroupFolder newAssociationObject(Group associate) {
		GroupFolder gf = new GroupFolder(associate, parent);
		parent.getGroupFolders().add(gf);
		associate.getGroupFolders().add(gf);
		return gf;
	}

	@Override
	protected void removeAssociationObject(GroupFolder ao) {
		parent.getGroupFolders().remove(ao);
		ao.getFolder().getGroupFolders().remove(ao);
	}

	@Override
	protected Group getAssociate(GroupFolder ao) {
		return ao.getGroup();
	}

}
