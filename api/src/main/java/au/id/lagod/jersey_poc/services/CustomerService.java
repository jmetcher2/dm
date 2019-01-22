package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.CustomersDTO;
import au.id.lagod.jersey_poc.links.LinkSpec;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class CustomerService extends BaseService {
	@Context FolderService folderService;

	@GET
	@Path("/")
	public CustomersDTO getCustomers() {
		return new CustomersDTO(model.getCustomers());
	}
	
	@GET
	@Path("{customerIdentifier}")
	public CustomerDTO getCustomer(@PathParam("customerIdentifier") String customerIdentifier) {
		return new CustomerDTO(model.customers(customerIdentifier));
	}
	
	public static LinkSpec customerLink(Customer customer) {
		return new LinkSpec(CustomerService.class, "getCustomer", param("customerIdentifier", customer.getIdentifier()));
	}
	
	public static LinkSpec customersLink() {
		return new LinkSpec(CustomerService.class, "getCustomers");
	}
	


}
