package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.PersonCustomerManager;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.jersey_poc.services.PersonService;

public class CustomerPersonsDTO extends BaseDTO {
	public Set<CustomerPersonDTO> customerPersons = new HashSet<CustomerPersonDTO>();
	
	public CustomerPersonsDTO() {}
	
	public CustomerPersonsDTO(PersonService service, PersonCustomerManager personCustomerManager) {
		for (CustomerPerson cp: personCustomerManager) {
			this.customerPersons.add(new CustomerPersonDTO(service, cp));
		}
		
		String userName = personCustomerManager.getParent().getUserName();
		_links.put("parent", service.getPerson(userName));
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
