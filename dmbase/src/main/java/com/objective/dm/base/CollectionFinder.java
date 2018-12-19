/**
 * 
 */
package com.objective.dm.base;

import static org.apache.commons.collections.CollectionUtils.filter;
import static org.apache.commons.collections.PredicateUtils.allPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.Predicate;

/**
 * @author jmetcher
 *
 */
public class CollectionFinder<T> extends BaseFinder<T> {
	
	protected Collection<T> collection;
	
	public CollectionFinder (Collection<T> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public List<T> find(Map<String, Object> params) {
		List<T> temp = new ArrayList<T>(this.collection);
		
		List<Predicate> predicates = mapToPredicates(params);
		
		filter(temp, allPredicate(predicates));
		
		return temp;
		
	}
	
	@Override
	public List<T> findAll() {
		return new ArrayList<T>(collection);
	}
	
	private List<Predicate> mapToPredicates(Map<String,Object> params) {

		// Create a predicate collection from the property name/value pairs in params
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (params != null) {
			Set<Map.Entry<String,Object>> entrySet = params.entrySet();
			for (Map.Entry<String,Object> entry: entrySet) {
				predicates.add(new BeanPropertyValueEqualsPredicate((String) entry.getKey(),entry.getValue(), true)); 
			}
		}
		
		return predicates;
	}

	
}
