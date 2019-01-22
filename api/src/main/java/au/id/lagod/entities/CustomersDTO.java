package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.RootService;

public class CustomersDTO extends BaseDTO {
	public Set<CustomerEmbedDTO> customers = new HashSet<CustomerEmbedDTO>();
	
	public CustomersDTO() {};
	
	public CustomersDTO(CustomerManager customers) {
		for (Customer c: customers) {
			this.customers.add(new CustomerEmbedDTO());
		}
		_links.addLink("this", CustomerService.customersLink());
		_links.addLink("parent", RootService.rootLink());
	}

}
