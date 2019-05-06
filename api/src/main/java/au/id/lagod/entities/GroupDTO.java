package au.id.lagod.entities;

import com.objective.keystone.model.group.Group;

import au.id.lagod.jersey_poc.services.GroupService;

public class GroupDTO extends BaseDTO {
	public Long id;
	public String type;
	public String name;
	public GroupFoldersDTO folders;
	
	public GroupDTO(GroupService service, Group group) {
		super();
		
		this.type = group.getType().toString();
		this.name = group.getName();
		
		String customerName = group.getCustomer().getIdentifier();
		
		this._links.put("self", service.getGroup(customerName, name));

		this.id = group.getId();
		this.folders = new GroupFoldersDTO(service, group.getGroupFolders());
		this._links.put("parent", service.getGroups(customerName));
	}

	public GroupDTO() {
	}

}
