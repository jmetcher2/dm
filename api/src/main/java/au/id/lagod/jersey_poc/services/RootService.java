package au.id.lagod.jersey_poc.services;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import au.id.lagod.entities.APIRoot;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Transactional
public class RootService extends BaseService {
	
	@GET
	@Path("/")
	public APIRoot getRoot() {
		return new APIRoot(this);
	}

}
