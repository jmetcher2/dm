package au.id.lagod.jersey_poc.config;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.EndpointConfigBase;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterModifier;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectWriterPlusUriInfo extends ObjectWriterModifier {

	private UriInfo uriInfo;

	public ObjectWriterPlusUriInfo(UriInfo uriInfo) {
	    this.uriInfo = uriInfo;
	}

	@Override
	public ObjectWriter modify(EndpointConfigBase<?> endpoint,
	        MultivaluedMap<String, Object> responseHeaders, Object value,
	        ObjectWriter ow, JsonGenerator gen) throws IOException {
	    // Adding the UriInfo attribute
	    return ow.withAttribute("JerseyUriInfo", uriInfo);
    }
}
