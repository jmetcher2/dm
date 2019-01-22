package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.objective.keystone.model.person.Person;

import au.id.lagod.entities.BaseDTO;
import au.id.lagod.entities.PersonDTO;
import au.id.lagod.entities.PersonsDTO;
import au.id.lagod.jersey_poc.links.LinkSpec;

@Path("/persons")
public class PersonService extends BaseService {

	@GET
	@Path("/")
	public PersonsDTO getPersons() {
		return new PersonsDTO(model.getPersons());
	}
	
	@GET
	@Path("{userName}")
	public BaseDTO getPerson(@PathParam("userName") String userName) {
		return new PersonDTO(model.persons(userName));
	}

	public static LinkSpec personsLink() {
		return new LinkSpec(PersonService.class, "getPersons");
	}
	public static LinkSpec personLink(Person person) {
		return new LinkSpec(PersonService.class, "getPersons", param("userName", person.getUserName()));
	}
}
