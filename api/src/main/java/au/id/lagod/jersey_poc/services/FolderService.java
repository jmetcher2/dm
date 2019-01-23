package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;

import au.id.lagod.entities.FolderDTO;
import au.id.lagod.entities.FoldersDTO;
import au.id.lagod.jersey_poc.links.LinkSpec;

@Path("/customers/{customerIdentifier}/folders")
public class FolderService extends BaseService {

	@GET
	@Path("/")
	public FoldersDTO getFolders(@PathParam("customerIdentifier") String customerIdentifier) {
		return new FoldersDTO(model.customers(customerIdentifier).getFolders());
	}
	
	@GET
	@Path("/{folderName}")
	public FolderDTO getFolder(@PathParam("customerIdentifier") String customerIdentifier,
			@PathParam("folderName") String folderName) {
		return new FolderDTO(model.customers(customerIdentifier).folders(folderName));
	}

	public static LinkSpec foldersLink(Customer customer) {
		return new LinkSpec(FolderService.class, "getFolders", param("customerIdentifier", customer.getIdentifier()));
	}

	public static LinkSpec folderLink(Folder folder) {
		return new LinkSpec(FolderService.class, "getFolder", param("customerIdentifier", folder.getCustomer().getIdentifier()),
				param("folderName", folder.getShortName()));
	}


}
