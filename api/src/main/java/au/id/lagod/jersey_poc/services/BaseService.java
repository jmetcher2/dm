package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class BaseService {

	@Autowired
	Model model;
	@Context
	protected UriInfo uriInfo;

	public BaseService() {
		super();
	}

}