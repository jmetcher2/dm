package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;

public class Customers {
	public Set<CustomerDTO> customers = new HashSet<CustomerDTO>();
	
	public Customers() {}
	
	public Customers(CustomerManager customers) {
		for (Customer c: customers) {
			this.customers.add(new CustomerDTO(c));
		}
	}

}
