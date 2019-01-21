package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;
import au.id.lagod.jersey_poc.services.RootService;

public class FoldersDTO extends BaseModel {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public FoldersDTO(UriInfo uriInfo) { super(uriInfo); }
	
	public FoldersDTO(UriInfo uriInfo, FolderManager folders) {
		super(uriInfo);
		for (Folder f: folders) {
			this.folders.add(new FolderDTO(uriInfo, f));
		}
		_links.addLink("this", FolderService.class);
		_links.addLink("parent", RootService.class);
		_links.addParametrizedLink ("parent", CustomerService.class, "getCustomer", 
				param("customerIdentifier", folders.getCustomer().getIdentifier())
				);

	}

}
