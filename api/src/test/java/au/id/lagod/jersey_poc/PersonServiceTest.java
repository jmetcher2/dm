package au.id.lagod.jersey_poc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

import au.id.lagod.entities.PersonDTO;
import au.id.lagod.entities.PersonsDTO;

public class PersonServiceTest extends BaseTest {
	
	@Override
	protected void doSetupBeforeTransaction() {
		doTeardownAfterTransaction();
		Customer c1 = model.getCustomers().create("test1");
		Customer c2 = model.getCustomers().create("test2");
		Person p1 = model.getPersons().create("testPerson1");
		Person p2 = model.getPersons().create("testPerson2");
		
		p1.getPersonCustomers().create(c1);
		p2.getPersonCustomers().create(c2);
		
	}

	@Test 
	public void testGetPerson() {
		Response response = rsGet(rootUrl + "/persons/testPerson1",MediaType.APPLICATION_JSON, "");
		//System.out.println(response.readEntity(String.class));
		PersonDTO person = response.readEntity(PersonDTO.class);
		
		assertTrue(person != null);
		
		assertEquals("testPerson1", person.userName);
		assertTrue(person.customerPersons.customerPersons.size() == 1);
	}
	
	@Test
	public void testGetPersons() {
		Response response = rsGet(rootUrl + "/persons",MediaType.APPLICATION_JSON, "");
		PersonsDTO persons = response.readEntity(PersonsDTO.class);
		
		System.out.println(persons);
		
		assertEquals(3, persons.persons.size());
	}
	

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers("testCustomer"));
		model.getCustomers().remove(model.customers("test1"));
		model.getCustomers().remove(model.customers("test2"));
		model.getPersons().remove(model.persons("testPerson1"));
		model.getPersons().remove(model.persons("testPerson2"));
	}


}
