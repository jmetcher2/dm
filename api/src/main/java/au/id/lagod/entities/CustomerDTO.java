package au.id.lagod.entities;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;

public class CustomerDTO extends BaseModel {
	public Long id;
	public String identifier;
	
	public CustomerDTO() {
	}
	
	public CustomerDTO (UriInfo uriInfo, Customer customer) {
		super(uriInfo);
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
		
		_links.addParametrizedLink ("self", CustomerService.class, "getCustomer", param("customerIdentifier", identifier) );
		_links.addParametrizedLink ("folders", FolderService.class, "getFolders", param("customerIdentifier", identifier) );
		_links.addLink("parent", CustomerService.class);
	}

}
