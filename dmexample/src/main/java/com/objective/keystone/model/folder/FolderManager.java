package com.objective.keystone.model.folder;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class FolderManager extends DomainObjectCollectionManager<Folder> {
	
	private Customer customer;
	
	public FolderManager(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public Folder create(String name) {
		Folder u = new Folder(customer, FolderType.folder, name, name);
		add(u);
		return u;
	}

	@Override
	public Class<Folder> getManagedObjectClass() {
		return Folder.class;
	}
	

}
