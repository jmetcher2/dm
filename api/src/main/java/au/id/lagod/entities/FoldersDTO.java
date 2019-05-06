package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.folder.AuthoringFolder;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;

import au.id.lagod.jersey_poc.services.FolderService;

public class FoldersDTO extends BaseDTO {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public FoldersDTO() {}
	
	public FoldersDTO(FolderService service, FolderManager folders) {
		super();
		for (Folder f: folders) {
			this.folders.add(new FolderDTO(service, f));
		}
		
		String customerIdf = folders.getCustomer().getIdentifier();
		
		_links.put("this", service.getFolders(customerIdf));
		_links.put("parent", service.getCustomerService().getCustomer(customerIdf));

	}

}
