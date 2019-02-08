package au.id.lagod.entities;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.FolderService;

public class FolderDTO extends BaseDTO {
	
	public String shortName;
	public Long id;
	public String folderType;
	
	public FolderDTO() {}

	public FolderDTO (FolderService service, Folder folder) {
		super(true, service);

		this.shortName = folder.getShortName();
		this.id = folder.getId();
		this.folderType = folder.getType().toString();

		_links.put("self", link("getFolder", folder, folder.getCustomer()));
		_links.put("parent", link("getFolders", folder.getCustomer()));
	}


}
