package au.id.lagod.entities;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.PersonService;

public class Links {
	
	public Map<String, Link> links = new HashMap<String, Link>();
	
	private UriInfo uriInfo;
	
	public Links(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	};
	
	/**
	 * These are convenience methods to provide a slightly cleaner way to call Jersey's UriBuilder methods.
	 * This just keeps the model classes a little more readable.
	 * 
	 * Note that we aren't calling the build() method.  The only effect that this has is that variable interpolation is not 
	 * performed.  This approach allows us to get back the literal service method path, placeholders and all (e.g."/requirements/{day}").  
	 * When we want the interpolated version, we'll need to provide another method that allows for passing in the values and then calls build()
	 * with those values.
	 * 
	 * @param rel
	 * @param clazz
	 * @param uriInfo
	 */
	public void addLink(String rel, Class<?> clazz) {
		links.put(rel, new Link(uriInfo.getBaseUriBuilder().path(clazz).toString()));

	}
	
	public void addLink(String rel, Class<?> clazz, String methodName) {
		links.put(rel, new Link(uriInfo.getBaseUriBuilder().path(clazz).path(clazz, methodName).toString()));

	}
	
	public void addParametrizedLink(String rel, Class<?> clazz, String methodName, String[] paramNames, Object[] paramValues, String[] queryParamNames, Object[] queryParamValues) {
		
		UriBuilder uriPath = uriInfo.getBaseUriBuilder().path(clazz).path(clazz, methodName);
		for(int  i=0; i< paramNames.length;i++){
			uriPath = uriPath.resolveTemplate(paramNames[i], paramValues[i]);
		}
		
		for(int  i=0; i< queryParamNames.length;i++){
			uriPath = uriPath.queryParam(queryParamNames[i], queryParamValues[i]);
		}
		
		links.put(rel, new Link(uriPath.build().toString()));

	}

	public void addParametrizedLink(String rel, Class<?> clazz, String methodName, LinkParameter... params) {
		UriBuilder uriPath = uriInfo.getBaseUriBuilder().path(clazz).path(clazz, methodName);
		for(int  i=0; i< params.length;i++){
			uriPath = uriPath.resolveTemplate(params[i].key, params[i].value);
		}
		links.put(rel, new Link(uriPath.build().toString()));
	}

}
