/**
 * 
 */
package au.id.lagod.dm.base.finders;

import static org.apache.commons.collections.CollectionUtils.filter;
import static org.apache.commons.collections.PredicateUtils.allPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.comparators.ComparatorChain;

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
		
		if (!params.getOrderBy().isEmpty()) {
			ComparatorChain chain = new ComparatorChain();
			
			for (OrderBy orderBy: params.getOrderBy()) {
				Comparator<T> comp = new BeanComparator<T>(orderBy.getFieldName());
				if (!orderBy.isAscending()) {
					comp = comp.reversed();
				}
				chain.addComparator(comp);
			}
			
			Collections.sort(temp, chain);
		}
		
		return temp;
		
	}

	private List<Predicate> speclistToPredicates(List<IFinderCriterion> params) {
		// Create a predicate collection from the finderspec list
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (params != null) {
			for (IFinderCriterion crit: params) {
				if (crit.isConjunction()) {
					FinderConjunction conj = (FinderConjunction) crit;
					Predicate lhPredicate = speclistToPredicates(List.of(conj.getLhs())).get(0);
					Predicate rhPredicate = speclistToPredicates(List.of(conj.getRhs())).get(0);
					
					if (ConjunctionOperator.AND.equals(conj.getOp())) {
						predicates.add(PredicateUtils.andPredicate(lhPredicate, rhPredicate));
					}
					else if (ConjunctionOperator.OR.equals(conj.getOp())) {
						predicates.add(PredicateUtils.orPredicate(lhPredicate, rhPredicate));
					}
					else if (ConjunctionOperator.NOT.equals(conj.getOp())) {
						predicates.add(PredicateUtils.notPredicate(lhPredicate));
					}
					
				}
				else {
					FinderCriterion entry = (FinderCriterion) crit;
					if (FinderOperator.EQUALS.equals(entry.getOp())) {
						predicates.add(new BeanPropertyValueEqualsPredicate(entry.getFieldName(),entry.getValue(), true));
					}
					else if (FinderOperator.CONTAINS.equals(entry.getOp())) {
						predicates.add(new BeanPropertyValueContainsPredicate(entry.getFieldName(),entry.getValue(), true));
					}
				}
			}
		}
		
		return predicates;
	}

	
}
