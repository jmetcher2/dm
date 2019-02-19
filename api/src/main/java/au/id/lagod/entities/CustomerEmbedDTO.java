package au.id.lagod.entities;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomerEmbedDTO extends BaseDTO {
	public Long id;
	public String identifier;
	
	public CustomerEmbedDTO() {
	}
	
	public CustomerEmbedDTO (CustomerService service, Customer customer) {
		super(true, service);
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.put("self", service.getCustomer(identifier));
	}

}
