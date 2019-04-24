package com.objective.keystone.model.folder;

import java.util.Set;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.customer.Customer;

public class ConsultFolderManager extends DomainObjectCollectionManager<ConsultFolder> {
	
	private Customer customer;
	
	public ConsultFolderManager(Customer customer, Set<ConsultFolder> consultFolderSet) {
		super(consultFolderSet);
		this.customer = customer;
	}

	public ConsultFolder createRoot() {
		ConsultFolder root = getRoot();
		if (root == null) {
			root = new ConsultFolder(customer, ConsultFolder.CONSULT_ROOT_NAME, ConsultFolder.CONSULT_ROOT_NAME, true);
			root.setParentManager(this);
			add(root);
		}
		return root;
	}

	public ConsultFolder getRoot() {
		System.out.println(this.collection);
		for (ConsultFolder f: getAll()) {
			if (f.isRoot())
				return f;
		}
		return null;
	}

	@Override
	protected ConsultFolder instantiate(String name) {
		return new ConsultFolder(customer, name, name, false);
	}

	@Override
	public Class<ConsultFolder> getManagedObjectClass() {
		return ConsultFolder.class;
	}

	public Customer getCustomer() {
		return customer;
	}
	

}
