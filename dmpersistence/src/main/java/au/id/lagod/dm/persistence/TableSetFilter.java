package au.id.lagod.dm.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface TableSetFilter<T> {

	public CriteriaQuery<T> filter(CriteriaQuery<T> query, Root<T> root, CriteriaBuilder cb);
}
