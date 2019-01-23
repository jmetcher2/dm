package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.CustomerPerson;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonDTO extends BaseDTO {
	public Long id;
	public String userName;
	public List<CustomerPersonDTO> customerPersons = new ArrayList<CustomerPersonDTO>();
	
	public PersonDTO() {}
	
	public PersonDTO (Person person) {
		this.userName = person.getUserName();
		this.id = person.getId();
		
		for (CustomerPerson cp: person.getPersonCustomers()) {
			customerPersons.add(new CustomerPersonDTO(cp));
		}
		
		_links.put("self", PersonService.personLink(person) );
		_links.put("parent", PersonService.personsLink());
	}

}
