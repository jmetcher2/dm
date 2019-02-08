package au.id.lagod.entities;

import au.id.lagod.jersey_poc.services.BaseService;
import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.PersonService;

public class APIRoot extends BaseDTO {
	public String name = "I am an API root";
	
	public APIRoot() {}
	
	public APIRoot(BaseService service) {
		super(false, service);
		
		_links.put("customers", link(CustomerService.class));
		_links.put("persons", link(PersonService.class));
		_links.put("this", link("getRoot"));
	}
	
}
