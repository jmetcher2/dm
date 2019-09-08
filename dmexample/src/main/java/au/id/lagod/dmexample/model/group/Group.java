package au.id.lagod.dmexample.model.group;

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
import au.id.lagod.dmexample.model.group.folder.GroupFolder;
import au.id.lagod.dmexample.model.group.folder.GroupFolderManager;

@Entity
@Table(name="groups")
public class Group extends BaseDomainObject implements ChildDomainObject {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")			protected Long id;
	
	@Column(name="description", length = 255)
	@NotBlank @Size(max=255)											protected String description;
	
	@TextKey
	@Column(name="label", length = 20)
	@NotBlank @Size(max=20)												protected String label;
	
	@ManyToOne
	@JoinColumn(name="customer_id")										protected Customer customer;
	
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
	protected Set<GroupFolder> folderSet = new HashSet<GroupFolder>();



	protected Group() {}
	
	protected Group(Customer customer, String code) {
		this.customer = customer;
		this.description = code;
		this.label = code;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public String getDescription() { return description; }
	public String getLabel() { return label; }

	@Override
	public BaseDomainObject getParent() {
		return customer;
	}
	
	public GroupFolderManager getGroupFolders() {
		return new GroupFolderManager(this, folderSet);
	}

	
}
