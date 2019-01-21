package au.id.lagod.entities;

import javax.ws.rs.core.UriInfo;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.FolderService;

public class FolderDTO extends BaseModel {
	
	public String shortName;
	public Long id;
	public String folderType;

	public FolderDTO (UriInfo uriInfo, Folder folder) {
		super(uriInfo);
		this.timestamp = null;
		this.shortName = folder.getShortName();
		this.id = folder.getId();
		this.folderType = folder.getType().toString();
		
		_links.addParametrizedLink ("self", FolderService.class, "getFolder", 
				param("customerIdentifier", folder.getCustomer().getIdentifier()),
				param("folderName", folder.getShortName())
				);
		_links.addParametrizedLink ("parent", FolderService.class, "getFolders", 
				param("customerIdentifier", folder.getCustomer().getIdentifier())
				);
	}


}
