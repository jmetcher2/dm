package au.id.lagod.dm.persistence;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import au.id.lagod.dm.base.BaseDomainObject;

public class TableSet<T extends BaseDomainObject>  extends AbstractSet<T> implements Set<T> {

	private SessionFactory sf;
	private Class<T> clazz;
	
	public TableSet(SessionFactory sf, Class<T> clazz) {
		this.sf = sf;
		this.clazz = clazz;
	}

	@Override
	public boolean add(T arg0) {
		sf.getCurrentSession().save(arg0);
		return true; // failed save will raise an exception
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		for (T obj: arg0) {
			add(obj);
		}
		return true;
	}

	@Override
	public void clear() {
		/*
		 * Must load the whole collection in order to clear it!
		 * There is no other way to ensure invariants are enforced - 
		 * if you want a quick delete, go and execute a SQL query and deal with the 
		 * cleanup yourself.
		 */
		Set<T> temp = new HashSet<T>(this);
		for (T obj: temp) {
			remove(obj);
		}
	}

	@Override
	public boolean contains(Object arg0) {
		/* This collection represent a whole database table (more correctly, a whole
		 * Hibernate mapping).
		 * Therefore if the object is the right type and it can be loaded, it is by
		 * definition contained in this collection 
		 */
		if (arg0 == null) 
			return false;
		
		if (!arg0.getClass().equals(clazz)) {
			return false;
		}
		
		// Fast ID-based retrieval for subclasses of BaseDomainObject
		BaseDomainObject obj = (BaseDomainObject) arg0;
		if (sf.getCurrentSession().get(clazz, obj.getId()) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Iterator<T> iterator() {
		if (!sf.getCurrentSession().isOpen()) {
			// Treat this set as empty
			return new HashSet<T>(0).iterator();
		}
		else {
			return new TableSetIterator<T>(this);
		}
	}
	
	protected Iterator<T> queryListIterator() {
		CriteriaQuery<T> query = sf.getCurrentSession().getCriteriaBuilder().createQuery(clazz);
		query.select(query.from(clazz));
		Query<T> q = sf.getCurrentSession().createQuery(query);
		return q.getResultList().iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		if (arg0 == null) {
			return false;
		}
		
		if (!(Hibernate.getClass(arg0).equals(clazz))) {
			return false;
		}

		sf.getCurrentSession().delete(arg0); // failed delete will raise an exception
		return true;
	}

	@Override
	public int size() {
		CriteriaBuilder builder = sf.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(clazz)));
		return sf.getCurrentSession().createQuery(query).getSingleResult().intValue();

	}

	@Override
	// The default implementation inherited from Abstract Set is to iterate over the whole collection.
	// This will either load the entire table, or, if there is no current session, crash.
	// Usually a constant hashcode is not advised as it results in inefficient lookups in hash-based collections.
	// However, there will usually be only a few TableSets in any given application.
	public int hashCode() {
		return 1;
	}
	
	


}
