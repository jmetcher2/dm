package au.id.lagod.entities;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomerEmbedDTO extends BaseDTO {
	public Long id;
	public String identifier;
	
	public CustomerEmbedDTO() {
	}
	
	public CustomerEmbedDTO (UriInfo uriInfo, Customer customer) {
		this.timestamp = null;
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.put ("self", CustomerService.customerLink(customer));
	}

}
