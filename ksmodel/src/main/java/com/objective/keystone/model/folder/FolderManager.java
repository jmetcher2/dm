package com.objective.keystone.model.folder;

import java.util.Set;

import com.objective.keystone.model.customer.Customer;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class FolderManager extends DomainObjectCollectionManager<Folder> {
	
	private Customer customer;
	
	public FolderManager(Customer customer, Set<Folder> folderSet) {
		super(folderSet);
		this.customer = customer;
	}

	public Folder createRoot() {
		return create(AuthoringFolder.ROOT_NAME, true);
	}

	public Folder createConsultRoot() {
		return createConsult(ConsultFolder.CONSULT_ROOT_NAME, true);
	}
	
	public ConsultFolder createConsult(String name) {
		return createConsult(name, false);
	}
	
	private AuthoringFolder create(String name, Boolean root) {
		AuthoringFolder f = new AuthoringFolder(customer, name, name, root); 
		f.setParentManager(this);
		add(f);
		return f;
	}
	
	private ConsultFolder createConsult(String name, Boolean root) {
		ConsultFolder f = new ConsultFolder(customer, name, name, root); 
		f.setParentManager(this);
		add(f);
		return f;
	}
	
	@Override
	public Folder create(String name) {
		return create(name, false);
	}
	

	public Folder getRoot() {
		for (Folder f: getAll()) {
			if (f.isAuthoringFolder() && f.isRoot())
				return f;
		}
		return null;
	}

	public Folder getConsultRoot() {
		for (Folder f: getAll()) {
			if (f.isConsultFolder() && f.isRoot())
				return f;
		}
		return null;
	}

	@Override
	protected Folder instantiate(String name) {
		return new AuthoringFolder(customer, name, name, false);
	}

	@Override
	public Class<Folder> getManagedObjectClass() {
		return Folder.class;
	}

	public Customer getCustomer() {
		return customer;
	}


}
