package au.id.lagod.entities;

import au.id.lagod.jersey_poc.services.RootService;

public class APIRoot extends BaseDTO {
	public String name = "I am an API root";
	
	public APIRoot() {}
	
	public APIRoot(RootService service) {
		super();
		
		_links.put("customers", service.getCustomerService().getCustomers());
		_links.put("persons", service.getPersonService().getPersons());
		_links.put("this", service.getRoot());
	}
	
}
