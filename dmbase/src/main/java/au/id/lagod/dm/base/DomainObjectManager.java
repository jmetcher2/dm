package au.id.lagod.dm.base;

import java.util.List;

/**
 * 
 * @author jmetcher
 *
 * @param <T>
 * 
 * Interface for managing collections of domain objects where 
 * formal Java collections semantics aren't needed or easily obtainable.
 * 
 * An example would be a manager for top level persistent objects that are
 * not composed within higher objects.  Hibernate's persistent collection model
 * can't be used here, so the manager may be based directly on a DAO and therefore not have
 * or need full set semantics.
 * 
 * @see DomainObjectCollection<T>
 * 
 */

public interface DomainObjectManager<T> extends Finder<T> {

	public T get(String textID);

	public T create(String name);
	
	public boolean remove(Object object);
	
	public List<T> getAll();
	
	public T getDefault();
}
	
