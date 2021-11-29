package au.id.lagod.dmexample.model.customer.folder;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.collections.DefaultFinderFactory;
import au.id.lagod.dmexample.model.customer.Customer;

public class FolderManager extends DomainObjectCollectionManager<Folder> {
	
	private Customer customer;
	
	public FolderManager(Collection<Folder> c) {
		super(c);
	}

	
	public FolderManager(Customer customer, Set<Folder> f) {
		super(f);
		this.customer = customer;
	}

	@Override
	protected Folder instantiate(String name) {
		return new Folder(customer, name);
	}

	@Override
	public Class<Folder> getManagedObjectClass() {
		return Folder.class;
	}

	public Customer getCustomer() {
		return customer;
	}
}
