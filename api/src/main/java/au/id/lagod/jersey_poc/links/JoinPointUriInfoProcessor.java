package au.id.lagod.jersey_poc.links;

import java.lang.reflect.Constructor;
import java.net.URI;

import javax.ws.rs.core.UriInfo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import au.id.lagod.entities.BaseDTO;
import au.id.lagod.jersey_poc.services.BaseService;

public class JoinPointUriInfoProcessor {
	
	public static <T extends BaseDTO> T process(JoinPoint thisJoinPoint) {
		MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		UriInfo uriInfo = ((BaseService) thisJoinPoint.getTarget()).getUriInfo();
		
		URI uri = uriInfo.getBaseUriBuilder()
		.path(signature.getDeclaringType())
		.path(signature.getMethod())
		.build(thisJoinPoint.getArgs());
		
		@SuppressWarnings("unchecked")
		Class<T> returnType = signature.getReturnType();
		
		try {
			Constructor<T> constructor = returnType.getDeclaredConstructor();
			T instance = constructor.newInstance();
			instance.selfLink = uri.toString();
			return instance;
		} catch (Exception e) {
			System.out.println("caught in processor");
			throw new Error(e);
		}
	}

}

