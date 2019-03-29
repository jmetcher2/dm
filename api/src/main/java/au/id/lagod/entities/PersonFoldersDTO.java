package au.id.lagod.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.FolderService;
import au.id.lagod.jersey_poc.services.PersonService;

public class PersonFoldersDTO extends BaseDTO {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public PersonFoldersDTO() {}
	
	public PersonFoldersDTO(PersonService service, Set<Folder> folders) {
		super();
		for (Folder f: folders) {
			this.folders.add(new FolderDTO(service.getFolderService(), f));
		}

	}
	
	public static class FolderDTO extends BaseEmbedDTO {
		
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
		}


	}


}
