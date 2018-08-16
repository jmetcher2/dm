package au.id.lagod.jersey_poc;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.CustomerDTO;
import au.id.lagod.entities.Customers;

public class RootServiceTest extends BaseTest {
	

	@Test
	public void testRootEndpoint() {
		Response response = rsGet(rootUrl,MediaType.APPLICATION_JSON, "");
		APIRoot apiRoot = response.readEntity(APIRoot.class);
		
		assertEquals("I am an API root", apiRoot.name);
	}
	
	@Test
	public void testGetCustomers() {
		Response response = rsGet(rootUrl + "/customers",MediaType.APPLICATION_JSON, "");
		Customers customers = response.readEntity(Customers.class);
		
		System.out.println(customers);
		
		assertEquals(3, customers.customers.size());
	}
	
	@Test 
	public void testGetCustomer() {
		Response response = rsGet(rootUrl + "/customers/test1",MediaType.APPLICATION_JSON, "");
		CustomerDTO customer = response.readEntity(CustomerDTO.class);
		
		assertTrue(customer != null);
		
		assertEquals("test1", customer.identifier);
	}
	
	@Override
	protected void doSetupBeforeTransaction() {
		model.getCustomers().create("test1");
		model.getCustomers().create("test2");
	}

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers("test1"));
		model.getCustomers().remove(model.customers("test2"));
	}


}
