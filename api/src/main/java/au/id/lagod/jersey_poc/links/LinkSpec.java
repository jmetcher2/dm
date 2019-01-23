package au.id.lagod.jersey_poc.links;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LinkSpecSerializer.class)
public class LinkSpec {
	Class<?> clazz;
	String methodName;
	LinkParameter[] params;
	
	public LinkSpec(Class<?> clazz, String methodName, LinkParameter... params) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
		this.params = params;
	}

	public String resolve(UriInfo uriInfo) {
		UriBuilder uriPath = uriInfo.getBaseUriBuilder().path(clazz).path(clazz, methodName);
		for(int  i=0; i< params.length;i++){
			uriPath = uriPath.resolveTemplate(params[i].key, params[i].value);
		}
		return (uriPath.build().toString());
	}
	
	
}
