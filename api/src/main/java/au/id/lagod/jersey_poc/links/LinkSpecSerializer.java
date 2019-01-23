package au.id.lagod.jersey_poc.links;

import java.io.IOException;

import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LinkSpecSerializer extends StdSerializer<LinkSpec> {
	
	private static final long serialVersionUID = 1L;

	public LinkSpecSerializer() {
        this(null);
    }
   
    public LinkSpecSerializer(Class<LinkSpec> t) {
        super(t);
    }
 
    @Override
    public void serialize(
    		LinkSpec value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
    	UriInfo uriInfo = (UriInfo)provider.getAttribute("JerseyUriInfo");
        
    	jgen.writeStartObject();
        jgen.writeStringField("href", value.resolve(uriInfo));
        jgen.writeEndObject();
    }
}
