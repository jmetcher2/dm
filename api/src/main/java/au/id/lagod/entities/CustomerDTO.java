package au.id.lagod.entities;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;
import au.id.lagod.jersey_poc.services.GroupService;

public class CustomerDTO extends BaseDTO {
	public Long id;
	public String identifier;
	
	public CustomerDTO() {
	}
	

	public CustomerDTO (CustomerService service, Customer customer) {
		super(false, service);
		
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.put("self", link("getCustomer",customer));
		_links.put("folders", link(FolderService.class, "getFolders", customer));
		_links.put("groups", link(GroupService.class, "getGroups",customer));
		_links.put("parent", link("getCustomers"));
	}

}
