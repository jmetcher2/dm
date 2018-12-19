package com.objective.dm.base;

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
import javax.validation.constraints.AssertTrue;

import org.apache.commons.beanutils.PropertyUtils;

import com.objective.dm.validators.Restricted;

public abstract class DomainObjectCollectionManager<T extends BaseDomainObject> implements Set<T>,
		DomainObjectManager<T> {

	protected BaseFinder<T> finder;	

	@Valid
	protected Collection<T> collection = new HashSet<T>(0);

	public DomainObjectCollectionManager() {
		this(new HashSet<T>(0));
	}

	public DomainObjectCollectionManager(Collection<T> c) {
		collection = c;
		finder = new CollectionFinder<T>(collection);
	}

	/**
	 * This method is strictly for object initialization.  When the initialization method mandates the use of the no-arg
	 * constructor (e.g. hydration by Hibernate) this setter is then invoked to complete initialization.  
	 */
	public void setCollection(Collection<T> c) {
		this.collection = c;
		finder = new CollectionFinder<T>(collection);
	}
	public Collection<T> getCollection() {
		return collection;
	}

	
	// TODO: should be protected
	public Finder<T> newFinder() {
		if (finder == null) {
			finder = new CollectionFinder<T>(collection);
		}
		return finder;
	}

	/*
	 * SET MANAGEMENT
	 * 
	 */
	public abstract Class<T> getManagedObjectClass();
	
	protected abstract T instantiate(String name);
	
	@Override
	public T create(String name) {
		T u = instantiate(name);
		u.setParentManager(this);
		add(u);
		return u;
	}
	
	public T get(String textID) {
		return findOne(BaseDomainObject.getTextKeyField(getManagedObjectClass()), textID);
	};
	
	public boolean exists(String textID) {
		return get(textID) != null;
	}
	
	public T getOrCreate(String name) {
		T t = get(name);
		if (t == null) {
			t = instantiate(name);
		}
		return t;
	}
	
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
	
	/*
	 * This add method and the AddDomainObject class below are just a basic default implementation
	 * If you need extra validation on add, override this method and create your own Add inner class
	 * 
	 * Note that this method is only public because the Set contract requires public visibility.  Most code should call a 
	 * create() method instead.
	 */
	@Restricted
	public boolean add(T o) {
		new AddDomainObject(o).execute();
		return true;
	}
	
	protected class AddDomainObject extends ValidatedCommand<Boolean> {
		
		@Valid 		private T domainObject;
		@AssertTrue private boolean nameIsUnique = true;

		public AddDomainObject(T domainObject) {
			this.domainObject = domainObject;
			
			if (domainObject.hasTextKey()) {
				this.nameIsUnique = get(domainObject.getTextKey()) == null;
			}
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
	/*
	 * DELEGATE TO FINDER
	 * 
	 */

	public List<T> find(Map<String, Object> params) {
		return newFinder().find(params);
	}

	// TODO should be part of finder interface
	public List<T> find(String paramName, Object value) {
		return newFinder().find(paramName, value);
	}

	public List<T> find(String[] paramNames, Object[] values) {
		return newFinder().find(paramNames, values);
	}

	public T findOne(Map<String, Object> params) {
		return newFinder().findOne(params);
	}

	// TODO should be part of finder interface
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
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 * 
	 * We have to rejig removeAll and retainAll to ensure that all
	 * element removals go via the remove() method.  This is the method that
	 * subclasses will override to clean up assocations before removal
	 * 
	 * Note that if DomainObjectCollectionManager was a Collection subclass, we 
	 * could rely on removeAll and retainAll calling our remove method and would not need to explictly
	 * re-route these calls via remove().  However, for ease of integration with Hibernate, we 
	 * instead have DomainObjectCollectionManager wrap a collection, so we need to manually ensure that the
	 * wrapper's remove method gets called by any Collection interface method that  might remove elements.
	 */
	public boolean removeAll(Collection<?> c) {
		for (Object o: c) {
			remove(o);
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
