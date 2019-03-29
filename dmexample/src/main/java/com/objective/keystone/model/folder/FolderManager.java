package com.objective.keystone.model.folder;

import java.util.Set;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.customer.Customer;

public class FolderManager extends DomainObjectCollectionManager<Folder> {
	
	private Customer customer;
	
	public FolderManager(Customer customer, Set<Folder> folderSet) {
		super(folderSet);
		this.customer = customer;
	}

	public Folder createRoot() {
		Folder root = getRoot();
		if (root == null) {
			root = new Folder(customer, Folder.ROOT_NAME, Folder.ROOT_NAME, true);
			root.setParentManager(this);
			add(root);
		}
		return root;
	}

	public Folder getRoot() {
		for (Folder f: getAll()) {
			if (f.isRoot())
				return f;
		}
		return null;
	}

@Override
	protected Folder instantiate(String name) {
		return new Folder(customer, name, name, false);
	}

	@Override
	public Class<Folder> getManagedObjectClass() {
		return Folder.class;
	}

	public Customer getCustomer() {
		return customer;
	}
	

}
