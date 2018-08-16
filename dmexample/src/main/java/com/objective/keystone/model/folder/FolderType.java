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
}
