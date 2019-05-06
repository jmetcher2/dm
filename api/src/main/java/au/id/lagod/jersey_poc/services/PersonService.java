package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.customer.CustomerPerson;

import au.id.lagod.entities.CustomerPersonDTO;
import au.id.lagod.entities.CustomerPersonsDTO;
import au.id.lagod.entities.PersonFoldersDTO;
import au.id.lagod.entities.PersonsDTO;
import au.id.lagod.entities.PersonDTO;

@Path("/persons")
public class PersonService extends BaseService {

	@GET
	@Path("/")
	public PersonsDTO getPersons() {
		return new PersonsDTO(this, model.getPersons());
	}
	
	@GET
	@Path("{userName}")
	public PersonDTO getPerson(@PathParam("userName") String userName) {
		return new PersonDTO(this, model.persons(userName));
	}
	
	@GET
	@Path("{userName}/customerpersons")
	public CustomerPersonsDTO getCustomerPersons(@PathParam("userName") String userName) {
		return new CustomerPersonsDTO(this, model.persons(userName).getPersonCustomers());
	}

	@GET
	@Path("{userName}/customerpersons/{customerIdentifier}")
	public CustomerPersonDTO getCustomerPerson(@PathParam("userName") String userName,
			@PathParam("customerIdentifier") String customerName) {
		Customer c = model.customers(customerName);
		CustomerPerson cp = model.persons(userName).getPersonCustomers().findOne("customer", c);
		return new CustomerPersonDTO(this, cp);
	}
	
	public CustomerPersonDTO getCustomerPerson(CustomerPerson cp) {
		// If you call a service method directly, you get back a skeleton DTO with the selfLink
		// field populated.  So this is one of those.
		return getCustomerPerson(cp.getPerson().getUserName(), cp.getCustomer().getIdentifier());
	}
	
	@GET
	@Path("{userName}/customerpersons/{customerIdentifier}/folders")
	public PersonFoldersDTO getFolders(@PathParam("userName") String userName,
			@PathParam("customerIdentifier") String customerName) {
		Customer c = model.customers(customerName);
		CustomerPerson cp = model.persons(userName).getPersonCustomers().findOne("customer", c);
		return new PersonFoldersDTO(this, cp.getFolders());
	}

}
