package au.id.lagod.jersey_poc.links;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import au.id.lagod.entities.BaseDTO;

public class LinkInterceptor implements WriterInterceptor {
	@Context UriInfo uriInfo;
	
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
                    throws IOException, WebApplicationException {
    	BaseDTO dto = (BaseDTO) context.getEntity();
    	dto.resolveLinkSpecs(uriInfo);
        context.proceed();
    }
}

