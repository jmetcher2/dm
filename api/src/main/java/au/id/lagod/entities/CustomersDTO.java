package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomersDTO extends BaseDTO {
	public Set<CustomerEmbedDTO> customers = new HashSet<CustomerEmbedDTO>();
	
	public CustomersDTO() {};
	
	public CustomersDTO(CustomerService service, CustomerManager customers) {
		super(false, service);
		
		for (Customer c: customers) {
			this.customers.add(new CustomerEmbedDTO(service, c));
		}
		_links.put("this", service.getCustomers());
		_links.put("parent", service.getRootService().getRoot());
	}

}
