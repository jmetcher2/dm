package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.group.folder.GroupFolder;
import com.objective.keystone.model.group.folder.GroupFolderManager;

import au.id.lagod.jersey_poc.services.GroupService;

public class GroupFoldersDTO extends BaseDTO {
	
	public List<FolderDTO> folders = new ArrayList<FolderDTO>();
	
	public GroupFoldersDTO() {
		super();
	}

	public GroupFoldersDTO(GroupService service, GroupFolderManager groupFolders) {
		super();
		for (GroupFolder f: groupFolders) {
			folders.add(service.getFolderService().getFolderDTO(f.getFolder()));
		}
	};
	
	

}
