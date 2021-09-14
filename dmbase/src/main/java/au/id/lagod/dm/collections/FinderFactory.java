package au.id.lagod.dm.collections;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.DomainCollectionManager;
import au.id.lagod.dm.base.Finder;

public interface FinderFactory {

	public <T extends BaseDomainObject> Finder<T> getFinder(DomainCollectionManager<T> manager);
	
}
