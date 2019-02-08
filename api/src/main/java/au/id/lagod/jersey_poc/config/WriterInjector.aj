package au.id.lagod.jersey_poc.config;

import javax.ws.rs.Path;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterInjector;

import au.id.lagod.jersey_poc.services.BaseService;

public aspect WriterInjector {
	pointcut resourceMethod(BaseService m) : execution(@Path * au.id.lagod.jersey_poc.services.BaseService+.* (..)) && target(m);
	
	 before (BaseService m) : resourceMethod(m) {
		ObjectWriterInjector.set(new ObjectWriterPlusUriInfo(m.getUriInfo()));
	}
}
