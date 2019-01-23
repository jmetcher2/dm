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
	
	public PersonsDTO(PersonManager persons) {
		for (Person p: persons) {
			this.persons.add(new PersonDTO(p));
		}
		
		_links.put("this", PersonService.personsLink());
		_links.put("parent", RootService.rootLink());
	}

}
