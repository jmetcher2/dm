package au.id.lagod.dm.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import au.id.lagod.dm.base.BaseFinder;


public class JPAFinder<T> extends BaseFinder<T> {

	protected SessionFactory sf;
	Class<T> clazz;
	Map<String,Object> globalCriteria;
	
	
	public JPAFinder(SessionFactory sf, Class<T> clazz) {
		this.sf = sf;
		this.clazz = clazz;
	}
	
	public JPAFinder(SessionFactory sf, Class<T> clazz, Map<String,Object> globalCriteria) {
		this.sf = sf;
		this.clazz = clazz;
		this.globalCriteria = globalCriteria;
	}
	
	@Override
	public List<T> find(Map<String,Object> params) {
		CriteriaBuilder cb = sf.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> cr = cb.createQuery(clazz);
		Root<T> root = cr.from(clazz);
		CriteriaQuery<T> cq = cr.select(root);
		
		Map<String,Object> allParams = new HashMap<>();
		if (globalCriteria != null) {
			allParams.putAll(globalCriteria);
		}

		if (allParams != null) {
			List<Predicate> predicates = mapToCriteria(cq, cb, root, params);  
			cq.where(predicates.toArray(new Predicate[] {}));
		}
		return sf.getCurrentSession().createQuery(cq).getResultList();
	}

	/*
	 * Builds criteria for a query by interpreting the map as field name/value pairs
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Predicate> mapToCriteria(CriteriaQuery<T> cq, CriteriaBuilder cb, From from, Map<String,Object> params) {
		List<Predicate> predicates = new ArrayList<>();
		for (String key: params.keySet()) {

				/*
				 * Parameters where the parameter value is a map are interpreted as applying to a composed
				 * object.  The key gives the field name of the composed object, and the map is then 
				 * interpreted as field names/values within that object
				 */
				if (params.get(key) instanceof Map) {
					Join j = from.join(key);
					predicates.addAll(mapToCriteria(cq, cb, j, (Map<String, Object>) params.get(key)));  // association criteria
				}
				
				/*
				 * If the field name contains a . it is interpreted as per the apache commons PropertyUtils convention
				 * The part of the name before the . is interpreted as the name of a field containing a composed object, and the part
				 * of the name after the . is interpreted as the name of a field within that object.
				 */
				else if (key.matches(".*\\..*")) {
					String [] temp = key.split("\\.");
					
					Join join = from.join(temp[0]);
					String value = temp[temp.length - 1];

					for (int i = 1; i < temp.length - 1; i++) {
						join = join.join(temp[i]);
					}
					
					predicates.add(cb.equal(join.get(value), params.get(key)));
				}
				
				else if (params.get(key) == null)
					predicates.add(cb.isNull(from.get(key)));
				else
					predicates.add(cb.equal(from.get(key), params.get(key)));
		}
		
		return predicates;
	}
	
	// TODO: these two methods ignore any global criteria set.  This means that 
	// Hibernate finders can leak objects from outside the aggregate - e.g. be used
	// to get experiences that aren't in the specified company

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return sf.getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Long id) {
		return (T) sf.getCurrentSession().get(clazz, id);
	}
		
}
