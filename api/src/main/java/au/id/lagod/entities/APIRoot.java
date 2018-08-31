package au.id.lagod.entities;

import javax.ws.rs.core.UriInfo;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.PersonService;
import au.id.lagod.jersey_poc.services.RootService;

public class APIRoot extends BaseModel {
	public String name = "I am an API root";
	
	public APIRoot(UriInfo uriInfo) {
		super(uriInfo);
		_links.addLink("customers", CustomerService.class);
		_links.addLink("persons", PersonService.class);
		_links.addLink("this", RootService.class);
	}
	
}
