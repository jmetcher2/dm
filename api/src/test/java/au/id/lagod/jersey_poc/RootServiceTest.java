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

public class RootServiceTest extends BaseTest {
	

	@Test
	public void testRootEndpoint() {
		Response response = rsGet(rootUrl,MediaType.APPLICATION_JSON, "");
		APIRoot apiRoot = response.readEntity(APIRoot.class);
		
		assertEquals("I am an API root", apiRoot.name);
	}

	@Override
	protected void doSetupBeforeTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doTeardownAfterTransaction() {
		// TODO Auto-generated method stub
		
	}

}