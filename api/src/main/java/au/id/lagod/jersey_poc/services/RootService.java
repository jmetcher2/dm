package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.objective.keystone.model.Model;

import au.id.lagod.entities.APIRoot;
import au.id.lagod.entities.Customers;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RootService {
	@Autowired Model model;

	@GET
	public APIRoot getRoot() {
		return new APIRoot();
	}
	
	@GET
	@Path("/customers")
	@Transactional
	public Customers getCustomers() {
		return new Customers(model.getCustomers());
	}
}
