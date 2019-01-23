package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderManager;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;

public class FoldersDTO extends BaseDTO {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public FoldersDTO(FolderManager folders) {
		for (Folder f: folders) {
			this.folders.add(new FolderDTO(f));
		}
		_links.put("this", FolderService.foldersLink(folders.getCustomer()));
		_links.put("parent", CustomerService.customerLink(folders.getCustomer()));

	}

}
