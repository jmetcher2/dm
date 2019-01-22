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

public class FoldersDTO extends BaseDTO {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public FoldersDTO(FolderManager folders) {
		for (Folder f: folders) {
			this.folders.add(new FolderDTO(f));
		}
		_links.addLink("this", FolderService.foldersLink(folders.getCustomer()));
		_links.addLink("parent", CustomerService.customerLink(folders.getCustomer()));

	}

}
