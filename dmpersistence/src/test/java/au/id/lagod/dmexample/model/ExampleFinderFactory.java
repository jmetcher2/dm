package au.id.lagod.dmexample.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.DomainCollectionManager;
import au.id.lagod.dm.base.Finder;
import au.id.lagod.dm.collections.DefaultFinderFactory;
import au.id.lagod.dm.persistence.JPAFinder;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.customer.CustomerManager;
import au.id.lagod.dmexample.model.customer.folder.FolderManager;

public class ExampleFinderFactory extends DefaultFinderFactory {
	
	SessionFactory sf;
	
	public ExampleFinderFactory(SessionFactory sf2) {
		this.sf = sf2;
	}

	@Override
	public <T extends BaseDomainObject> Finder<T> getFinder(DomainCollectionManager<T> manager) {
		if (sf != null) {  // Are we connected to the DB?
			
			if (manager.getClass().equals(CustomerManager.class)) {
				return new JPAFinder<T>(sf, manager.getManagedObjectClass());
			}
			
			if (manager.getClass().equals(FolderManager.class)) {
				Customer c = ((FolderManager) manager).getCustomer();
				Map<String, Object> criteria = Map.of("customer", c);
				return new JPAFinder<T>(sf, manager.getManagedObjectClass(), criteria);
			}
			
		}
		return super.getFinder(manager);
	}

}
