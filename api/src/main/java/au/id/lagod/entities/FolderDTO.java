package au.id.lagod.entities;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.FolderService;

public class FolderDTO extends BaseDTO {
	
	public String shortName;
	public Long id;
	public String folderType;

	public FolderDTO (Folder folder) {
		this.timestamp = null;
		this.shortName = folder.getShortName();
		this.id = folder.getId();
		this.folderType = folder.getType().toString();

		_links.put("self", FolderService.folderLink(folder));
		_links.put("parent", FolderService.foldersLink(folder.getCustomer()));
	}


}
