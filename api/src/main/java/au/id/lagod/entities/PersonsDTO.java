package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonsDTO extends BaseDTO {
	public Set<PersonDTO> persons = new HashSet<PersonDTO>();
	private PersonService service;
	
	public PersonsDTO() {}
	
	public PersonsDTO(PersonService service, PersonManager persons) {
		super();
		this.service = service;
		
		for (Person p: persons) {
			this.persons.add(new PersonDTO(service, p));
		}
		
		_links.put("self", service.getPersons());
		_links.put("parent", service.getRootService().getRoot());
	}

	public static class PersonDTO extends BaseEmbedDTO {
		public Long id;
		public String userName;
		public List<CustomerPersonDTO> customerPersons = new ArrayList<CustomerPersonDTO>();
		
		public PersonDTO() {}
		
		public PersonDTO (PersonService service, Person person) {
			
			this.userName = person.getUserName();
			this.id = person.getId();
			for (CustomerPerson cp: person.getPersonCustomers()) {
				this.customerPersons.add(new CustomerPersonDTO(cp));
			}
			
			_links.put("self", service.getPerson(userName) );
		
		}
	}

	public static class CustomerPersonDTO extends BaseEmbedDTO {

		public String type;
		public Long customerId;
		
		public CustomerPersonDTO() {}

		public CustomerPersonDTO(CustomerPerson cp) {
			this.type = cp.getType().toString();
			this.customerId = cp.getCustomer().getId();
		}

	}

}
