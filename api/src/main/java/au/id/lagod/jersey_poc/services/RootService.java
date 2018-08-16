package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.Customers;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class RootService {
	@Autowired Model model;
	
	@GET
	public APIRoot getRoot() {
		return new APIRoot();
	}
	
	@GET
	@Path("/customers")
	public Customers getCustomers() {
		return new Customers(model.getCustomers());
	}
	
	@GET
	@Path("/customers/{customerIdentifier}")
	public CustomerDTO getCustomer(@PathParam("customerIdentifier") String customerIdentifier) {
		return new CustomerDTO(model.customers(customerIdentifier));
	}
}
