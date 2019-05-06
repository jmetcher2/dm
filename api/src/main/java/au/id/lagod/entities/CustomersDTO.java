package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomersDTO extends BaseDTO {
	public Set<CustomerDTO> customers = new HashSet<CustomerDTO>();
	
	public CustomersDTO() {};
	
	public CustomersDTO(CustomerService service, CustomerManager customers) {
		super();
		
		for (Customer c: customers) {
			this.customers.add(new CustomerDTO(service, c));
		}
		_links.put("this", service.getCustomers());
		_links.put("parent", service.getRootService().getRoot());
	}
	
	public static class CustomerDTO extends BaseEmbedDTO {
		public Long id;
		public String identifier;
		
		public CustomerDTO() {
		}
		
		public CustomerDTO (CustomerService service, Customer customer) {
			super();
			
			this.identifier = customer.getIdentifier();
			this.id = customer.getId();
			
			_links.put("self", service.getCustomer(identifier));
		}

	}

}
