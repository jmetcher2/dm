package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import au.id.lagod.entities.BaseModel;
import au.id.lagod.entities.PersonDTO;
import au.id.lagod.entities.PersonsDTO;

@Path("/persons")
public class PersonService extends BaseService {

	@GET
	@Path("/")
	public PersonsDTO getPersons() {
		return new PersonsDTO(uriInfo, model.getPersons());
	}
	
	@GET
	@Path("{userName}")
	public BaseModel getPerson(@PathParam("userName") String userName) {
		return new PersonDTO(uriInfo, model.persons(userName));
	}
}
