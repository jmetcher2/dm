package au.id.lagod.entities;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomerDTO extends BaseDTO {
	public Long id;
	public String identifier;
	
	public CustomerDTO() {
	}
	
	public CustomerDTO (CustomerService service, Customer customer) {
		super();
		
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.put("self", service.getCustomer(identifier));
		_links.put("folders", service.getFolderService().getFolders(identifier));
		_links.put("groups", service.getGroupService().getGroups(identifier));
		_links.put("parent", service.getCustomers());
	}

}
