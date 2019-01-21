package com.objective.keystone.model.folder;

public enum FolderType {
	lostfound{
        public String toString() {
            return "lost+found";
        }
	},
	consult,
	linked,
	root,
	folder,
	consult_root;
	
	public static FolderType fromString(String s) {
		switch(s) {
			case "lost+found": return FolderType.lostfound;
			case "linked": return FolderType.linked;
			case "root": return FolderType.root;
			case "folder": return FolderType.folder;
			case "consult_root": return FolderType.consult_root;
			case "consult": return FolderType.consult;
			default: 
				throw new IllegalArgumentException("FolderType [" + s
	                    + "] not supported.");		
		}
	}
}
