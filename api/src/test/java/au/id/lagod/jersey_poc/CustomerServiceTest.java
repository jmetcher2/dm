package au.id.lagod.jersey_poc;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.CustomersDTO;

public class CustomerServiceTest extends BaseTest {
	
	@Test
	public void testGetCustomers() {
		Response response = rsGet(rootUrl + "/customers",MediaType.APPLICATION_JSON, "");
		CustomersDTO customers = response.readEntity(CustomersDTO.class);
		
		System.out.println(customers);
		
		assertEquals(3, customers.customers.size());
	}
	
	@Override
	protected void doSetupBeforeTransaction() {
		Customer c1 = model.getCustomers().create("test1");
		Customer c2 = model.getCustomers().create("test2");
		
	}

	@Test 
	public void testGetCustomer() {
		Response response = rsGet(rootUrl + "/customers/test1",MediaType.APPLICATION_JSON, "");
		CustomerDTO customer = response.readEntity(CustomerDTO.class);
		
		assertTrue(customer != null);
		
		assertEquals("test1", customer.identifier);
	}
	
	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers("test1"));
		model.getCustomers().remove(model.customers("test2"));
	}


}
