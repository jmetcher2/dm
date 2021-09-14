package au.id.lagod.dm.collections;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.CollectionFinder;
import au.id.lagod.dm.base.DomainCollectionManager;
import au.id.lagod.dm.base.Finder;

public class DefaultFinderFactory implements FinderFactory {

	@Override
	public <T extends BaseDomainObject> Finder<T> getFinder(DomainCollectionManager<T> manager) {
		return new CollectionFinder<T>(manager.getCollection());
	}

}
