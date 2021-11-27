package au.id.lagod.dm.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import au.id.lagod.dm.base.finders.Finder;
import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.collections.DefaultFinderFactory;
import au.id.lagod.dm.collections.FinderFactory;
import au.id.lagod.dm.validators.Restricted;

@Configurable
public abstract class DomainCollectionManager<T extends BaseDomainObject> implements Set<T>, Finder<T> {

	/** 
	 * Spring configuration into a transient class requires compile with aspectj and the spring aspects.
	 * The alternative is to stash the finder factory in a static variable of a well-known class.  
	 * The aspect configuration itself is static, so these options  amount to about 
	 * the same thing: multiple spring contexts can overwrite each other's static global state.
	 * To avoid this:
	 *  - don't use multiple overlapping contexts
	 *  - you can if necessary "evict" aspect state by calling aspectOf() delete method
	 *  I've found the second only to be necessary in test code when using the Spring test framework, which
	 *  has a habit of opening and caching multiple contexts.
	 */
	@Autowired
	FinderFactory finderFactory;
	
	protected Finder<T> finder;
	@Valid
	protected Collection<T> collection = new HashSet<T>(0);

	public DomainCollectionManager() {
		this(new HashSet<T>(0));
	}

	public DomainCollectionManager(Collection<T> c) {
		collection = c;
		this.finderFactory = new DefaultFinderFactory();
	}


	/**
	 * This method is strictly for object initialization.  When the initialization method mandates the use of the no-arg
	 * constructor (e.g. hydration by Hibernate) this setter is then invoked to complete initialization.  
	 */
	public void setCollection(Collection<T> c) {
		this.collection = c;
	}

	public Collection<T> getCollection() {
		return collection;
	}

	public Finder<T> newFinder() {
		if (finder == null) {
			finder = finderFactory.getFinder(this);
		}
		return finder;
	}

	public abstract Class<T> getManagedObjectClass();

	public List<T> getAll() {
		return new ArrayList<T>(collection);
	}

	public T getDefault() {
		if (collection.isEmpty()) {
			return null;
		}
		else {
			return collection.iterator().next();
		}
	}

	protected boolean addnocheck(T object) {
		return collection.add(object);
	}

	@Restricted
	public boolean add(T o) {
		new AddDomainObject(o).execute();
		return true;
	}

	/*
	 * DELEGATE TO FINDER
	 * 
	 */


	public List<T> find(Map<String, Object> params) {
		return newFinder().find(params);
	}

	public List<T> find(List<FinderSpec> params) {
		return newFinder().find(params);
	}

	public List<T> find(String paramName, Object value) {
		return newFinder().find(paramName, value);
	}

	public List<T> find(String[] paramNames, Object[] values) {
		return newFinder().find(paramNames, values);
	}

	public T findOne(Map<String, Object> params) {
		return newFinder().findOne(params);
	}

	public T findOne(String paramName, Object value) {
		return newFinder().findOne(paramName, value);
	}

	public T findOne(String[] paramNames, Object[] values) {
		return newFinder().findOne(paramNames, values);
	}

	@Override
	public List<T> findAll() {
		return newFinder().findAll();
	}
	
	@Override
	public T findById(Long id) {
		return newFinder().findById(id);
	}

	/*
	 * DELEGATE TO COLLECTION
	 */

	public boolean addAll(Collection<? extends T> c) {		
		for (T o: collection) {			
			add(o);
		}
		return collection.addAll(c);
	}

	public void clear() {
		// can't remove from a collection inside an iterator, so we iterate over a copy
		Collection<T> temp = new HashSet<T>(collection);
		for (Object o: temp) {
			remove(o);
		}
		collection.clear();
	}

	public boolean contains(Object o) {
		return collection.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public Iterator<T> iterator() {
		return collection.iterator();
	}

	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		if (contains(o)) {
			((T) o).removeAssociates();
		}
		return collection.remove(o);
	}

	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> c) {
		if (c.equals(collection) || c.equals(this)) {
			Iterator<?> iterator = c.iterator();
			while (iterator.hasNext()) {
				Object o = iterator.next();
				if (contains(o)) {
					((T) o).removeAssociates();
				}
				iterator.remove();
			}
		}
		else {
			for (Object o: c) {
				remove(o);
			}
		}
		return true;
	}

	public boolean retainAll(Collection<?> c) {
		// Copy our collection
		Collection<T> temp = new ArrayList<T>(collection);
		
		// Remove the "to retain" elements from the copy
		// temp now contains the elements to remove
		temp.removeAll(c);
		
		return removeAll(temp);
	}

	public int size() {
		return collection.size();
	}

	public Object[] toArray() {
		return collection.toArray();
	}

	public <S> S[] toArray(S[] a) {
		return collection.toArray(a);
	}
	
	protected class AddDomainObject extends ValidatedCommand<Boolean> {
		
		@Valid 		private T domainObject;

		public AddDomainObject(T domainObject) {
			this.domainObject = domainObject;
		}
		
		@Override
		public Boolean doCommand() {
			return addnocheck(domainObject);
		}
		
		@Override
		public String getMessage(Set<ConstraintViolation<ValidatedCommand<Boolean>>> constraintViolations) {
			return super.getMessage(constraintViolations) + " | " + domainObject.getMessage();
		}

		
	}


	/**
	 * Flattens nested collections
	 * 
	 * The name parameter is similar to the Apache PropertyUtils convention, but currently only
	 * properties that are subclasses of DomainObjectCollectionManager are supported.
	 * 
	 * Example: 
	 * companies.flatten("venues.experiences") - returns a list of all experiences in all venues in all companies.
	 * 
	 * @param name A dot separated list of property names
	 * @return A list of domain objects contained in the collections reachable by the name parameter
	 */
	public <S extends BaseDomainObject> List<S> flatten(String name) {
		List<String> names = Arrays.asList(name.split("\\."));
		List<S> values = new ArrayList<S>();
		return flatten(names, values);
	}

	@SuppressWarnings("unchecked")
	protected <S extends BaseDomainObject> List<S> flatten(List<String> names, List<S> values) {
		String name = names.get(0);
		for (T o: collection) {
			Object value;
			try {
				value = PropertyUtils.getSimpleProperty(o, name);
				if (value instanceof DomainObjectCollectionManager) {
					List<String> nextLevelNames = new ArrayList<String>(names);
					nextLevelNames.remove(0);
					if (nextLevelNames.size() == 0) {
						values.addAll((DomainObjectCollectionManager<S>) value);
					}
					else {
						((DomainObjectCollectionManager<S>) value).flatten(nextLevelNames, values);
					}
				}
				else if (value instanceof BaseDomainObject) {
					List<String> nextLevelNames = new ArrayList<String>(names);
					nextLevelNames.remove(0);
					if (nextLevelNames.size() == 0) {
						values.add((S) value);
					}
					else {
						((S) value).flatten(nextLevelNames, values);
					}
				}
				else {
					// TODO: Here we'd want to continue down the name list until we reach another DomainObjectCollectionManager
					throw new java.lang.Error ("Not handling this case yet");
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return values;
	}

}