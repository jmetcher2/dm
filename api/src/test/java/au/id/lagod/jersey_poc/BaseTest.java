package au.id.lagod.jersey_poc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.Before;

import com.objective.dm.test.BasePersistenceTests;

public abstract class BaseTest extends BasePersistenceTests {
	protected static String defaultRootUrl = "http://localhost:8080/api/resources";
	protected static String rootUrl = System.getProperty("apiRootUrl") == null ? defaultRootUrl : System.getProperty("apiRootUrl");
	
	private Client rsClient;

	@Before
	public void setup() {
		ClientConfig clientConfig = new ClientConfig();
		
		rsClient = ClientBuilder.newClient(clientConfig);
	}
	
	@After
	public void teardown() {
		
	}
	
	protected ClientWrapper getClient() {
		return new ClientWrapper(rsClient, rootUrl);
	}

	protected Response rsGet(String url, String mediaType, String authHeader) {
		WebTarget rsEndPoint = rsClient.target(url);
		

		Builder temp = rsEndPoint
		.property(ClientProperties.FOLLOW_REDIRECTS, false)
		.request(mediaType) 
		.header("Authorization",authHeader)
		.header("Origin","javax.ws.rs.client.WebTarget");
		
		return temp.get();
	}

	protected Response rsGet(String url) {
		return rsGet(url, MediaType.APPLICATION_JSON, "");
	}


}
