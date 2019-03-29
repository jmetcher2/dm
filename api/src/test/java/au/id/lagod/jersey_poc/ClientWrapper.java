package au.id.lagod.jersey_poc;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.BaseDTO;
import au.id.lagod.entities.BaseEmbedDTO;

public class ClientWrapper {
	private BaseDTO entity;
	private Client rsClient;
	
	public ClientWrapper(Response response) {
		entity = response.readEntity(APIRoot.class);
	}
	
	public ClientWrapper(Client rsClient, String rootUrl) {
		this(rsClient, rootUrl, APIRoot.class);
	}

	public ClientWrapper(Client rsClient, String rootUrl, Class<? extends BaseDTO> clazz) {
		this.rsClient = rsClient;
		entity = rsGet(rootUrl).readEntity(clazz);
	}

	public ClientWrapper follow(String rel, Class clazz) {
		return new ClientWrapper(rsClient, entity._links.get(rel), clazz);
	}

	
	public <T extends BaseEmbedDTO> ClientWrapper find(Function<BaseDTO, Set<T>> getCollection, Predicate<T> p, Class clazz) {
		T newEntity = getCollection.apply(entity).stream().filter(p).collect(Collectors.toList()).get(0);
		return new ClientWrapper(rsClient, newEntity._links.get("self"), clazz);
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

	public BaseDTO getEntity() {
		return entity;
	}

	

}
