package au.id.lagod.entities;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.FolderService;

public class FolderDTO extends BaseDTO {
	
	public String shortName;
	public Long id;
	public String folderType;
	
	public FolderDTO() {}

	public FolderDTO (FolderService service, Folder folder) {
		super();

		this.shortName = folder.getShortName();
		this.id = folder.getId();
		this.folderType = folder.getType().toString();

		String customerName = folder.getCustomer().getIdentifier();
		
		_links.put("self", service.getFolder(customerName, shortName));
		_links.put("parent", service.getFolders(customerName));
	}


}
