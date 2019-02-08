package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;

import au.id.lagod.jersey_poc.services.PersonService;
import au.id.lagod.jersey_poc.services.RootService;

public class PersonsDTO extends BaseDTO {
	public Set<PersonDTO> persons = new HashSet<PersonDTO>();
	
	public PersonsDTO() {}
	
	public PersonsDTO(PersonService service, PersonManager persons) {
		super(false, service);
		
		for (Person p: persons) {
			this.persons.add(new PersonDTO(service, p, true));
		}
		
		_links.put("this", link("getPersons"));
		_links.put("parent", link(RootService.class, "getRoot"));
	}

}
