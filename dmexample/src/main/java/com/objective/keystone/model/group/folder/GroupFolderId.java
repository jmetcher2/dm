package com.objective.keystone.model.group.folder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class GroupFolderId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="folder_id") private Long folderId;
	@Column(name="group_id") private Long groupId;
	
	public GroupFolderId() { }
	
	public GroupFolderId(Long folderId, Long groupId) {
		super();
		this.folderId = folderId;
		this.groupId = groupId;
	}

	public Long getFolderId() {
		return folderId;
	}

	public Long getGroupId() {
		return groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((folderId == null) ? 0 : folderId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupFolderId other = (GroupFolderId) obj;
		if (folderId == null) {
			if (other.folderId != null)
				return false;
		} else if (!folderId.equals(other.folderId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	};
	
	
}
