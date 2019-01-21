package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import au.id.lagod.entities.FolderDTO;
import au.id.lagod.entities.FoldersDTO;

@Path("/customers/{customerIdentifier}/folders")
public class FolderService extends BaseService {

	@GET
	@Path("/")
	public FoldersDTO getFolders(@PathParam("customerIdentifier") String customerIdentifier) {
		return new FoldersDTO(uriInfo, model.customers(customerIdentifier).getFolders());
	}
	
	@GET
	@Path("/{folderName}")
	public FolderDTO getFolder(@PathParam("customerIdentifier") String customerIdentifier,
			@PathParam("folderName") String folderName) {
		return new FolderDTO(uriInfo, model.customers(customerIdentifier).folders(folderName));
	}
}
