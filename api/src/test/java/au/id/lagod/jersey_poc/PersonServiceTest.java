package au.id.lagod.jersey_poc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.objective.keystone.fixture.Basic;

import au.id.lagod.entities.CustomerPersonDTO;
import au.id.lagod.entities.CustomerPersonsDTO;
import au.id.lagod.entities.PersonDTO;
import au.id.lagod.entities.PersonFoldersDTO;
import au.id.lagod.entities.PersonsDTO;
import au.id.lagod.entities.PersonFoldersDTO.FolderDTO;


public class PersonServiceTest extends BaseTest {
	
	private static final String PREFIX = "PST";


	@Override
	protected void doSetupBeforeTransaction() {
		Basic fixture = new Basic(PREFIX);
		fixture.setup(model, sf);
	}

	@Test 
	public void testGetPerson() {
		Response response = rsGet(rootUrl + "/persons/" + PREFIX + "testPerson1",MediaType.APPLICATION_JSON, "");
		//System.out.println(response.readEntity(String.class));
		PersonDTO person = response.readEntity(PersonDTO.class);
		
		assertTrue(person != null);
		
		assertEquals(PREFIX + "testPerson1", person.userName);
		assertTrue(person.customerPersons.size() == 2);
	}
	
	@Test
	public void testGetPersons() {
		Response response = rsGet(rootUrl + "/persons",MediaType.APPLICATION_JSON, "");
		PersonsDTO persons = response.readEntity(PersonsDTO.class);
		
		System.out.println(persons);
		
		assertTrue(persons.persons.size() >= 3);
	}
	

	@Test 
	public void testPersonFolders() {
		Set<String> foldersFound = new HashSet<String>();
		String personId = PREFIX + "testPerson1";
		String customerId = PREFIX + "test1";
		
		Predicate<PersonsDTO.PersonDTO> findUser =  e -> e.userName.equals(personId);
		Predicate<CustomerPersonsDTO.CustomerPersonDTO> findCustomerPerson =  e -> e.customerIdentifier.equals(customerId);
		
		PersonFoldersDTO foldersDto = (PersonFoldersDTO) getClient()
		.follow("persons", PersonsDTO.class)  // get the persons collection
		.find(c -> ((PersonsDTO) c).persons, findUser, PersonDTO.class) // find our user
		.follow("customerpersons", CustomerPersonsDTO.class) // get their customerPersons collection
		.find(c -> ((CustomerPersonsDTO) c).customerPersons, findCustomerPerson, CustomerPersonDTO.class) // find the customerPerson relating to our customer
		.follow("folders", PersonFoldersDTO.class)  // and get their folders
		.getEntity();
		
		Set<FolderDTO> folders = foldersDto.folders;
		for (FolderDTO f: folders) {
			foldersFound.add(f.shortName);
		}
		
		assertTrue(foldersFound.contains(PREFIX+"testFolder1"));
		assertTrue(foldersFound.contains(PREFIX+"testFolder2"));
	}
	
	@Override
	protected void doTeardownAfterTransaction() {
		Basic fixture = new Basic(PREFIX);
		fixture.teardown(model);
	}


}
