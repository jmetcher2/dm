package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.entities.BaseDTO;
import au.id.lagod.jersey_poc.services.PersonService;

public class PersonDTO extends BaseDTO {
	public Long id;
	public String userName;
	public List<CustomerPersonDTO> customerPersons = new ArrayList<CustomerPersonDTO>();
	
	public PersonDTO() {}
	
	public PersonDTO (PersonService service, Person person) {
		super();
		
		this.userName = person.getUserName();
		this.id = person.getId();
		for (CustomerPerson cp: person.getPersonCustomers()) {
			this.customerPersons.add(new CustomerPersonDTO(service, cp));
		}
		
		_links.put("parent", service.getPersons());
		_links.put("customerpersons", service.getCustomerPersons(userName));
		_links.put("self", service.getPerson(userName) );
	
	}
	
	public static class CustomerPersonDTO extends BaseEmbedDTO {

		public String type;
		public Long customerId;
		public String customerIdentifier;
		
		public CustomerPersonDTO() {}

		public CustomerPersonDTO(PersonService service, CustomerPerson cp) {
			this.type = cp.getType().toString();
			this.customerId = cp.getCustomer().getId();
			this.customerIdentifier = cp.getCustomer().getIdentifier();
			
			_links.put("self", service.getCustomerPerson(cp));

			
		}

	}

}
