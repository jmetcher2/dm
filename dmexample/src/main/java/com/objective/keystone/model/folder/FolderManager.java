package com.objective.keystone.model.folder;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.customer.Customer;

public class FolderManager extends DomainObjectCollectionManager<Folder> {
	
	private Customer customer;
	
	public FolderManager(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	protected Folder instantiate(String name) {
		return new Folder(customer, FolderType.folder, name, name);
	}

	@Override
	public Class<Folder> getManagedObjectClass() {
		return Folder.class;
	}
	

}
