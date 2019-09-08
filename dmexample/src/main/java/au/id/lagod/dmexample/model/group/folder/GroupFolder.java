package au.id.lagod.dmexample.model.group.folder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import au.id.lagod.dm.base.BaseAssociationDomainObject;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.group.Group;

@Entity
@Table(name="group_folders")
public class GroupFolder  extends BaseAssociationDomainObject<Group, Folder> {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)	
														protected Long id;
	
	@ManyToOne 
	@JoinColumn(name="group_id")				protected Group group;

	@ManyToOne 
	@JoinColumn(name="folder_id")				protected Folder folder; 

	protected GroupFolder() {}
	
	protected GroupFolder(Group group, Folder folder) {
		this.group = group;
		this.folder = folder;
	}
	
	@Override
	public Long getId() { return id; }
	public Group getGroup() { return group; }
	public Folder getFolder() { return folder; }
	
}
