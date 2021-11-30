package au.id.lagod.dm.base.finders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FinderSpec {
	
	private List<IFinderCriterion> criteria = new ArrayList<IFinderCriterion>();
	
	private List<String> orderBy = new ArrayList<String>();
	
	private PagingRange paging;
	
	public FinderSpec(String field, Object value) {
		criteria.add(new FinderCriterion(field, FinderOperator.EQUALS, value));
	}
	
	public  FinderSpec(Map<String, Object> params) {
		criteria = mapToSpecList(params);
	}
	
	public FinderSpec() {
	}

	public List<IFinderCriterion> getCriteria() {
		return criteria;
	}

	public List<String> getOrderBy() {
		return orderBy;
	}

	public PagingRange getPaging() {
		return paging;
	}

	public FinderSpec addCriterion(String fieldName, FinderOperator op, Object fieldValue) {
		criteria.add(new FinderCriterion(fieldName, op, fieldValue));
		return this;
	};
	
	public FinderSpec addCriterion(IFinderCriterion crit) {
		criteria.add(crit);
		return this;
	};
	
	public FinderSpec paging(boolean specifiedByPage, int pageSizeOrItemStart, int pageNumberOrItemEnd) {
		this.paging = new PagingRange(specifiedByPage, pageSizeOrItemStart, pageNumberOrItemEnd);
		return this;
	}
	
	public static List<IFinderCriterion> mapToSpecList(Map<String, Object> params) {
		List<IFinderCriterion> specs = new ArrayList<>();
		for (Entry<String, Object> entry: params.entrySet()) {
			IFinderCriterion spec = new FinderCriterion(entry.getKey(), FinderOperator.EQUALS, entry.getValue());
			specs.add(spec);
		}
		return specs;
	}


}
