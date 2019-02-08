package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.PersonCustomerManager;

import au.id.lagod.jersey_poc.services.PersonService;

public class CustomerPersonsDTO extends BaseDTO {
	public List<CustomerPersonDTO> customerPersons = new ArrayList<CustomerPersonDTO>();
	
	public CustomerPersonsDTO() {}
	
	public CustomerPersonsDTO(PersonService service, PersonCustomerManager personCustomerManager) {
		for (CustomerPerson cp: personCustomerManager) {
			this.customerPersons.add(new CustomerPersonDTO(service, cp, true));
		}
		
		_links.put("parent", link("getPerson", personCustomerManager.getParent()));
	}
}
