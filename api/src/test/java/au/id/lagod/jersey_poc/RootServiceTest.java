package au.id.lagod.jersey_poc;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import au.id.lagod.entities.APIRoot;

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
