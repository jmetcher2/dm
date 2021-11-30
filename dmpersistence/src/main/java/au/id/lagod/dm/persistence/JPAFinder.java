package au.id.lagod.dm.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import au.id.lagod.dm.base.finders.BaseFinder;
import au.id.lagod.dm.base.finders.ConjunctionOperator;
import au.id.lagod.dm.base.finders.FinderConjunction;
import au.id.lagod.dm.base.finders.FinderOperator;
import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.base.finders.IFinderCriterion;
import au.id.lagod.dm.base.finders.FinderCriterion;


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
	public List<T> find(FinderSpec params) {
		CriteriaBuilder cb = sf.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<T> cr = cb.createQuery(clazz);
		Root<T> root = cr.from(clazz);
		CriteriaQuery<T> cq = cr.select(root);
		
		List<IFinderCriterion> allParams = new ArrayList<>(params.getCriteria());
		if (globalCriteria != null) {
			allParams.addAll(FinderSpec.mapToSpecList(globalCriteria));
		}

		if (allParams != null) {
			List<Predicate> predicates = specsToCriteria(cb, root, allParams);  
			cq.where(predicates.toArray(new Predicate[] {}));
		}
		
		Query<T> q = sf.getCurrentSession().createQuery(cq);
		if (params.getPaging() != null) {
			q.setFirstResult(params.getPaging().start);
			q.setMaxResults(params.getPaging().pageSize);
		}
		
		return q.getResultList();
	}

	/*
	 * Builds criteria for a query by interpreting the map as field name/value pairs
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Predicate> specsToCriteria(CriteriaBuilder cb, From from, List<IFinderCriterion> params) {
		List<Predicate> predicates = new ArrayList<>();
		for (IFinderCriterion crit: params) {
			if (crit.isConjunction()) {
				
				FinderConjunction conj = (FinderConjunction) crit;
				Predicate lhPredicate = specsToCriteria(cb, from, List.of(conj.getLhs())).get(0);
				Predicate rhPredicate = specsToCriteria(cb, from, List.of(conj.getRhs())).get(0);
				
				if (ConjunctionOperator.AND.equals(conj.getOp())) {
					predicates.add(cb.and(lhPredicate, rhPredicate));
				}
				else if (ConjunctionOperator.OR.equals(conj.getOp())) {
					predicates.add(cb.or(lhPredicate, rhPredicate));
				}
				else if (ConjunctionOperator.NOT.equals(conj.getOp())) {
					predicates.add(cb.not(lhPredicate));
				}
			}
			else {
				FinderCriterion spec = (FinderCriterion) crit;
				String key = spec.getFieldName();

				/*
				 * Parameters where the parameter value is a map are interpreted as applying to a composed
				 * object.  The key gives the field name of the composed object, and the map is then 
				 * interpreted as field names/values within that object
				 */
				if (spec.getValue() instanceof Map) {
					Join j = from.join(key);
					List<IFinderCriterion> nestedSpecs = FinderSpec.mapToSpecList((Map<String, Object>) spec.getValue());
					predicates.addAll(specsToCriteria(cb, j, nestedSpecs));  // association criteria
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
					
					Predicate predicate = predicateForOperator(spec.getOp(), cb, join.get(value), spec.getValue());
					predicates.add(predicate);
				}
				
				else {
					Predicate predicate = predicateForOperator(spec.getOp(), cb, from.get(key), spec.getValue());
					predicates.add(predicate);
				}
			}
		}
		
		return predicates;
	}
	
	
	private Predicate predicateForOperator(FinderOperator op, CriteriaBuilder cb, Path path, Object value) {
		if (value == null) {
			return cb.isNull(path);
		}
		else if (FinderOperator.EQUALS.equals(op)) {
			return cb.equal(path,  value);
		}
		else if (FinderOperator.CONTAINS.equals(op)) {
			return cb.like(path, "%" + (String) value + "%");
		}
		else {
			throw new RuntimeException("Unsupported find operator "  + op);
		}
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
