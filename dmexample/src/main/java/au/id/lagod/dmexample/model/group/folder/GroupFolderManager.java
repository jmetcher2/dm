package au.id.lagod.dmexample.model.group.folder;

import java.util.Collection;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.group.Group;

public class GroupFolderManager extends AssociationCollectionManager<Group, GroupFolder, Folder> {

	public GroupFolderManager(Group parent, Collection<GroupFolder> c) {
		super(parent, c, "folder",
				Folder.class,
				GroupFolder.class);
	}

	@Override
	protected GroupFolder newAssociationObject(Folder associate) {
		GroupFolder gf = new GroupFolder(parent, associate);
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
	protected Folder getAssociate(GroupFolder ao) {
		return ao.getFolder();
	}

}
