package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

import au.id.lagod.entities.Link;
import au.id.lagod.jersey_poc.links.LinkParameter;
import au.id.lagod.jersey_poc.links.LinkSpec;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class BaseService {

	@Autowired
	Model model;

	public BaseService() {
		super();
	}

	protected static LinkParameter param(String key, Object value) {
		return new LinkParameter(key, value);
	}
	
}