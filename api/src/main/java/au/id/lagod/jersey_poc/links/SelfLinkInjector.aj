package au.id.lagod.jersey_poc.links;

import javax.ws.rs.Path;

import au.id.lagod.jersey_poc.services.BaseService;

public aspect SelfLinkInjector {
	pointcut resourceMethod(BaseService m) : call(@Path * au.id.lagod.jersey_poc.services.BaseService+.* (..)) && 
		target(m);
	
	 Object around (BaseService m) : resourceMethod(m)  {
		return JoinPointUriInfoProcessor.process(thisJoinPoint);
	}


}
