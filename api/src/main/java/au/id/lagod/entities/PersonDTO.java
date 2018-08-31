package au.id.lagod.entities;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.person.Person;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonDTO extends BaseModel {
	public Long id;
	public String userName;
	
	public PersonDTO (UriInfo uriInfo, Person customer) {
		super(uriInfo);
		this.userName = customer.getUserName();
		this.id = customer.getId();
		
		_links.addParametrizedLink("self", PersonService.class, "getPerson",  param("userName", userName) );
		_links.addLink("parent", PersonService.class);
	}

}
