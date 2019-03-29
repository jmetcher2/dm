package au.id.lagod.jersey_poc.services;

import javax.inject.Provider;
import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class BaseService {

	@Context
	protected UriInfo uriInfo;

	@Autowired
	Model model;

	public BaseService() {
		super();
	}

	public UriInfo getUriInfo() {
		return uriInfo;
	}
	
	@Context 
	Provider<CustomerService> customerService;
	public CustomerService getCustomerService() {
		return customerService.get();
	}

	@Context 
	Provider<FolderService> folderService;
	public FolderService getFolderService() {
		return folderService.get();
	}
	
	@Context 
	Provider<GroupService> groupService;
	public GroupService getGroupService() {
		return groupService.get();
	}
	
	@Context 
	Provider<RootService> rootService;
	public RootService getRootService() {
		return rootService.get();
	}

	@Context 
	Provider<PersonService> personService;
	public PersonService getPersonService() {
		return personService.get();
	}
	
	@Context 
	Provider<EventService> eventService;
	public EventService getEventService() {
		return eventService.get();
	}
	
}