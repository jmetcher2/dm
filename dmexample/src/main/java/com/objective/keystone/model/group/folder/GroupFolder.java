package com.objective.keystone.model.group.folder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.objective.dm.base.AssociationParents;
import com.objective.dm.base.BaseAssociationDomainObject;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;

@Entity
@Table(name="publisher_folder_group_lnk")
public class GroupFolder extends BaseAssociationDomainObject<Group, Folder> {

	@EmbeddedId
	private GroupFolderId eid = new GroupFolderId();
	
	public GroupFolderId getEid() {
		return eid;
	}
	
	@MapsId("groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id")
	private Group group;
	
	@MapsId("folderId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="folder_id")
	private Folder folder;
	
	protected GroupFolder() {}
	
	public GroupFolder(Group group, Folder folder) {
		this.group = group;
		this.folder = folder;
	}
	
	@Override
	public AssociationParents<Group, ? extends BaseAssociationDomainObject<Group, Folder>, Folder> getAssociationParents() {
		return new AssociationParents<Group, GroupFolder, Folder>(group.getGroupFolders(), folder.getGroups());
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Group getGroup() {
		return group;
	}

	public Folder getFolder() {
		return folder;
	}
	
	

}
