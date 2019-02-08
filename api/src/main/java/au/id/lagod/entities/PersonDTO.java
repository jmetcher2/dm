package au.id.lagod.entities;

import java.util.List;

import com.objective.keystone.model.person.Person;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonDTO extends BaseDTO {
	public Long id;
	public String userName;
	public List<CustomerPersonDTO> customerPersons;
	
	public PersonDTO() {}
	
	public PersonDTO (PersonService service, Person person, Boolean embed) {
		super(embed, service);
		
		this.userName = person.getUserName();
		this.id = person.getId();
		
		if (!embed) {
			_links.put("parent", link("getPersons"));
		}

		_links.put("customerpersons", link("getCustomerPersons", person));
		_links.put("self", link("getPerson", person) );
	
	}
}
