package au.id.lagod.entities;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;

public class CustomerDTO extends BaseDTO {
	public Long id;
	public String identifier;
	
	public CustomerDTO() {
	}
	

	public CustomerDTO (Customer customer) {
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.addLink("self", CustomerService.customerLink(customer));
		_links.addLink("folders", FolderService.foldersLink(customer));
		_links.addLink("parent", CustomerService.customersLink());
	}

}
