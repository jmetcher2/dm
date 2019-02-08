package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.group.GroupManager;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.GroupService;

public class GroupsDTO extends BaseDTO {
	public Set<GroupDTO> groups = new HashSet<GroupDTO>();
	
	public GroupsDTO() {}
	
	public GroupsDTO(GroupService service, GroupManager groups) {
		super(false, service);
		for (Group g: groups) {
			this.groups.add(new GroupDTO(service, g, true));
		}
		_links.put("this", link("getGroups", groups.getCustomer()));
		_links.put("parent", link(CustomerService.class, "getCustomer", groups.getCustomer()));

	}

}
