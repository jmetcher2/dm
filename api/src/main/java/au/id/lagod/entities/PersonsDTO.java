package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;

import au.id.lagod.jersey_poc.services.PersonService;
import au.id.lagod.jersey_poc.services.RootService;

public class PersonsDTO extends BaseModel {
	public Set<PersonDTO> persons = new HashSet<PersonDTO>();
	
	public PersonsDTO(UriInfo uriInfo, PersonManager persons) {
		super(uriInfo);
		
		for (Person p: persons) {
			this.persons.add(new PersonDTO(uriInfo, p));
		}
		
		_links.addLink("this", PersonService.class);
		_links.addLink("parent", RootService.class);
	}

}
