package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.CustomersDTO;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class CustomerService extends BaseService {
	@Context FolderService folderService;

	@GET
	@Path("/")
	public CustomersDTO getCustomers() {
		return new CustomersDTO(this, model.getCustomers());
	}
	
	@GET
	@Path("{customerIdentifier}")
	public CustomerDTO getCustomer(@PathParam("customerIdentifier") String customerIdentifier) {
		return new CustomerDTO(this, model.customers(customerIdentifier));
	}
	
}
