package au.id.lagod.entities;

import com.objective.keystone.model.customer.Customer;

public class CustomerDTO {
	public Long id;
	public String identifier;
	
	public CustomerDTO() {}
	
	public CustomerDTO (Customer customer) {
		this.identifier = customer.getIdentifier();
		this.id = customer.getId();
	}

}
