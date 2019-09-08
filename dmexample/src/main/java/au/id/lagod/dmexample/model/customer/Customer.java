package au.id.lagod.dmexample.model.customer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.TextKey;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.customer.folder.FolderManager;
import au.id.lagod.dmexample.model.group.Group;
import au.id.lagod.dmexample.model.group.GroupManager;

@Entity
@Table(name="customers")
public class Customer extends BaseDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")			protected Long id;
	
	@Column(name="name", length = 255)
	@NotBlank @Size(max=255)											protected String name;
	
	@TextKey
	@Column(name="code", length = 20)
	@NotBlank @Size(max=20)												protected String code;
	
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Group> groupSet = new HashSet<Group>();

	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "customer", orphanRemoval = true)				protected Set<Folder> folderSet = new HashSet<Folder>();


	protected Customer() {}
	
	protected Customer(String code) {
		this.name = code;
		this.code = code;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public String getName() { return name; }
	public String getCode() { return code; }
	
	public GroupManager getGroups() {
		return new GroupManager(this, groupSet);
	}
	public Group groups(String label) {
		return getGroups().get(label);
	}

	public FolderManager getFolders() {
		return new FolderManager(this, folderSet);
	}
	public Folder folders(String name) {
		return getFolders().get(name);
	}
}
