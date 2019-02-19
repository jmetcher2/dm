package au.id.lagod.entities;

import com.objective.keystone.model.person.Person;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonDTO extends BaseDTO {
	public Long id;
	public String userName;
	public CustomerPersonsDTO customerPersons;
	
	public PersonDTO() {}
	
	public PersonDTO (PersonService service, Person person, Boolean embed) {
		super(embed, service);
		
		this.userName = person.getUserName();
		this.id = person.getId();
		this.customerPersons = new CustomerPersonsDTO(service, person.getPersonCustomers());
		
		if (!embed) {
			_links.put("parent", service.getPersons());
		}
		
		_links.put("customerpersons", service.getCustomerPersons(userName));
		_links.put("self", service.getPerson(userName) );
	
	}
}
