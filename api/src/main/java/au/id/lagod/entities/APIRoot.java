package au.id.lagod.entities;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.PersonService;
import au.id.lagod.jersey_poc.services.RootService;

public class APIRoot extends BaseDTO {
	public String name = "I am an API root";
	
	public APIRoot() {
		_links.addLink("customers", CustomerService.customersLink());
		_links.addLink("persons", PersonService.personsLink());
		_links.addLink("this", RootService.rootLink());
	}
	
}
