package au.id.lagod.dm.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import au.id.lagod.dm.base.finders.BaseFinder;
import au.id.lagod.dm.base.finders.FinderCriterion;
import au.id.lagod.dm.base.finders.FinderSpec;


public class HibernateFinder<T> extends BaseFinder<T> {

	protected SessionFactory sf;
	Class clazz;
	Map<String,Object> globalCriteria;
	
	
	public HibernateFinder(SessionFactory sf, Class clazz) {
		this.sf = sf;
		this.clazz = clazz;
	}
	
	public HibernateFinder(SessionFactory sf, Class clazz, Map<String,Object> globalCriteria) {
		this.sf = sf;
		this.clazz = clazz;
		this.globalCriteria = globalCriteria;
	}
	
	public Criteria newCriteria() {
		Criteria criteria = sf.getCurrentSession().createCriteria(clazz);
		//criteria.setCacheable(true);
		if (globalCriteria != null) {
			criteria = mapToCriteria(criteria, FinderSpec.mapToSpecList(globalCriteria));
		}
		return criteria;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> find(FinderSpec params) {
		Criteria criteria = newCriteria();
		if (params == null) {
			return criteria.list();
		}
		else {
			return mapToCriteria(criteria, params.getCriteria()).list();  // non-generic hibernate function
		}
	}

	/*
	 * Builds criteria for a query by interpreting the map as field name/value pairs
	 */
	@SuppressWarnings("unchecked")
	public Criteria mapToCriteria(Criteria criteria, List<FinderCriterion> params) {
		Map<String,Criteria> nestedCriteriaMap = new HashMap<String,Criteria>();

		for (FinderCriterion fc: params) {
			String key = fc.getFieldName();
			Object value = fc.getValue();

				/*
				 * Parameters where the parameter value is a map are interpreted as applying to a composed
				 * object.  The key gives the field name of the composed object, and the map is then 
				 * interpreted as field names/values within that object
				 */
				if (value instanceof Map) {
					mapToCriteria(criteria.createCriteria(key), FinderSpec.mapToSpecList((Map<String, Object>) value));  // association criteria
				}
				
				/*
				 * If the field name contains a . it is interpreted as per the apache commons PropertyUtils convention
				 * The part of the name before the . is interpreted as the name of a field containing a composed object, and the part
				 * of the name after the . is interpreted as the name of a field within that object.
				 */
				else if (key.matches(".*\\..*")) {
					String [] temp = key.split("\\.", 2);
					
					Map<String,Object> nestedParams = new HashMap<String, Object>();
					nestedParams.put(temp[1], value);
					
					// Remember if we've already created a subcriteria for this association, and if so use the same one
					// Hibernate doesn't like two subcriteria on the same association.
					if (!nestedCriteriaMap.containsKey(temp[0])) {
						nestedCriteriaMap.put(temp[0], criteria.createCriteria(temp[0]));
					}
					Criteria nestedCriteria = nestedCriteriaMap.get(temp[0]);
					
					mapToCriteria(nestedCriteria,FinderSpec.mapToSpecList(nestedParams));
				}
				
				else if (value == null)
					criteria.add(Restrictions.isNull(key));
				else
					criteria.add(Restrictions.eq(key,value));
		}
		
		return criteria;
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
