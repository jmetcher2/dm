package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.objective.keystone.model.group.Group;

import au.id.lagod.entities.GroupDTO;
import au.id.lagod.entities.GroupsDTO;

@Path("/customers/{customerIdentifier}/groups")
public class GroupService extends BaseService {
	@Context
	FolderService folderService;

	@GET
	@Path("/")
	public GroupsDTO getGroups(@PathParam("customerIdentifier") String customerIdentifier) {
		return new GroupsDTO(this, model.customers(customerIdentifier).getGroups());
	}
	
	@GET
	@Path("/{groupName}")
	public GroupDTO getGroup(@PathParam("customerIdentifier") String customerIdentifier,
			@PathParam("groupName") String groupName) {
		return getGroupDTO(model.customers(customerIdentifier).groups(groupName), false);
	}

	public FolderService getFolderService() {
		return folderService;
	}
	
	public GroupDTO getGroupDTO(Group group, boolean embed) {
		return new GroupDTO(this, group, embed);
	}

}
