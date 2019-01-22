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

public class PersonsDTO extends BaseDTO {
	public Set<PersonDTO> persons = new HashSet<PersonDTO>();
	
	public PersonsDTO() {}
	
	public PersonsDTO(PersonManager persons) {
		for (Person p: persons) {
			this.persons.add(new PersonDTO(p));
		}
		
		_links.addLink("this", PersonService.personsLink());
		_links.addLink("parent", RootService.rootLink());
	}

}
