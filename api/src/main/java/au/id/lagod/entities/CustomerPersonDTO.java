package au.id.lagod.entities;

import com.objective.keystone.model.person.customer.CustomerPerson;

import au.id.lagod.jersey_poc.services.CustomerService;

public class CustomerPersonDTO extends BaseDTO {

	public String type;
	
	public CustomerPersonDTO() {}

	public CustomerPersonDTO(CustomerPerson cp) {
		this.type = cp.getType().toString();
		
		_links.addLink("customer", CustomerService.customerLink(cp.getCustomer()));
	}

}
