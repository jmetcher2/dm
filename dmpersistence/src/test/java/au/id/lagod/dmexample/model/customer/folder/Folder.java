package au.id.lagod.dmexample.model.customer.folder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.ChildDomainObject;
import au.id.lagod.dm.base.TextKey;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.group.folder.FolderGroupManager;
import au.id.lagod.dmexample.model.group.folder.GroupFolder;

@Entity
@Table(name="folders")
public class Folder extends BaseDomainObject implements ChildDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")			protected Long id;
	
	@TextKey
	@Column(name="name", length = 20)
	@NotBlank @Size(max=20)												protected String name;
	
	@ManyToOne
	@JoinColumn(name="customer_id")										protected Customer customer;

	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
	protected Set<GroupFolder> groupSet = new HashSet<GroupFolder>();

	protected Folder() {}
	
	protected Folder(Customer customer, String code) {
		this.customer = customer;
		this.name = code;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public String getName() { return name; }

	@Override
	public BaseDomainObject getParent() {
		return customer;
	}

	public FolderGroupManager getGroupFolders() {
		return new FolderGroupManager(this, groupSet);
	}


}
