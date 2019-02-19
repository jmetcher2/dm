package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.customer.CustomerPerson;

import au.id.lagod.entities.CustomerPersonDTO;
import au.id.lagod.entities.CustomerPersonsDTO;
import au.id.lagod.entities.PersonDTO;
import au.id.lagod.entities.PersonFoldersDTO;
import au.id.lagod.entities.PersonsDTO;

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
		return new PersonDTO(this, model.persons(userName), false);
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
		return new CustomerPersonDTO(this, cp, false);
	}

	@GET
	@Path("{userName}/folders/{customerIdentifier}")
	public PersonFoldersDTO getFolders(@PathParam("userName") String userName,
			@PathParam("customerIdentifier") String customerName) {
		Customer c = model.customers(customerName);
		CustomerPerson cp = model.persons(userName).getPersonCustomers().findOne("customer", c);
		return new PersonFoldersDTO(this, cp.getFolders());
	}

}
