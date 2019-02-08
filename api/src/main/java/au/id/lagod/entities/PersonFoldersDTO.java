package au.id.lagod.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.objective.keystone.model.folder.Folder;

import au.id.lagod.jersey_poc.services.PersonService;

public class PersonFoldersDTO extends BaseDTO {
	public Set<FolderDTO> folders = new HashSet<FolderDTO>();
	
	public PersonFoldersDTO() {}
	
	public PersonFoldersDTO(PersonService service, List<Folder> folders) {
		super(false, service);
		for (Folder f: folders) {
			this.folders.add(service.getFolderService().getFolderDTO(f));
		}

	}

}
