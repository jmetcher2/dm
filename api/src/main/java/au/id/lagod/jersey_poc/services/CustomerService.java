package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.CustomersDTO;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class CustomerService extends BaseService {

	@GET
	@Path("/")
	public CustomersDTO getCustomers() {
		return new CustomersDTO(uriInfo, model.getCustomers());
	}
	
	@GET
	@Path("{customerIdentifier}")
	public CustomerDTO getCustomer(@PathParam("customerIdentifier") String customerIdentifier) {
		return new CustomerDTO(uriInfo, model.customers(customerIdentifier));
	}
}
