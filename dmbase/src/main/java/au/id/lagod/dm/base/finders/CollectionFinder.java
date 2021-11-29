/**
 * 
 */
package au.id.lagod.dm.base.finders;

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
	public List<T> findAll() {
		return new ArrayList<T>(collection);
	}
	
	@Override
	public T findById(Long id) {
		return super.findbyId(id);
	}

	@Override
	public List<T> find(FinderSpec params) {
		List<T> temp = new ArrayList<T>(this.collection);
		
		List<Predicate> predicates = speclistToPredicates(params.getCriteria());
		
		filter(temp, allPredicate(predicates));
		
		return temp;
		
	}

	private List<Predicate> speclistToPredicates(List<FinderCriterion> params) {
		// Create a predicate collection from the finderspec list
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (params != null) {
			for (FinderCriterion entry: params) {
				if (FinderOperator.EQUALS.equals(entry.getOp())) {
					predicates.add(new BeanPropertyValueEqualsPredicate(entry.getFieldName(),entry.getValue(), true));
				}
				else if (FinderOperator.CONTAINS.equals(entry.getOp())) {
					predicates.add(new BeanPropertyValueContainsPredicate(entry.getFieldName(),entry.getValue()));
				}
			}
		}
		
		return predicates;
	}

	
}
