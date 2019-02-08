package au.id.lagod.jersey_poc.services;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.Person;
import com.objective.dm.base.BaseDomainObject;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class BaseService {
	
	private static Map<Class<? extends BaseDomainObject>, String> entityParamNames =  new HashMap<Class<? extends BaseDomainObject>, String>();
	static {
		entityParamNames.put(Customer.class, "customerIdentifier");
		entityParamNames.put(Folder.class, "folderName");
		entityParamNames.put(Group.class, "groupName");
		entityParamNames.put(Person.class, "userName");
	}
	
	@Context
	protected UriInfo uriInfo;

	@Autowired
	Model model;

	public BaseService() {
		super();
	}

	public UriInfo getUriInfo() {
		return uriInfo;
	}
	
	public <T extends BaseDomainObject> String paramName(T o) {
		if (o == null) return "null";
		
		return entityParamNames.get(o.getClass());
	}
	
}