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
	private PersonService service;
	
	public PersonDTO() {}
	
	public PersonDTO (PersonService service, Person person) {
		super();
		this.service = service;
		
		this.userName = person.getUserName();
		this.id = person.getId();
		for (CustomerPerson cp: person.getPersonCustomers()) {
			this.customerPersons.add(new CustomerPersonDTO(cp));
		}
		
		_links.put("parent", service.getPersons());
		_links.put("customerpersons", service.getCustomerPersons(userName));
		_links.put("self", service.getPerson(userName) );
	
	}
	
	public class CustomerPersonDTO extends BaseEmbedDTO {

		public String type;
		public List<GroupDTO> groups = new ArrayList<GroupDTO>();
		public Long customerId;
		
		public CustomerPersonDTO() {}

		public CustomerPersonDTO(CustomerPerson cp) {
			this.type = cp.getType().toString();
			this.customerId = cp.getCustomer().getId();
			
			for (CustomerPersonGroup cpg: cp.getCustomerPersonGroups()) {
				groups.add(service.getGroupService().getGroupDTO(cpg.getGroup(), true));
			}
			
			_links.put("self", service.getCustomerPerson(cp));

			
		}

	}

}
