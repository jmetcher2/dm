package au.id.lagod.dm.base.finders;

import java.util.List;
import java.util.Map;

public interface Finder<T> {

	/**
	 * This is the core find function that subclasses must implement
	 * All the other find functions are convience methods that translate their
	 * parameters into a Map, then call this function
	 * @param params
	 * @return
	 */
	public abstract List<T> find(Map<String, Object> params);

	public abstract List<T> find(FinderSpec spec);

	public abstract List<T> findAll();

	public abstract List<T> find(String[] paramNames, Object[] values);

	public abstract List<T> find(String paramName, Object value);

	public abstract T findOne(String paramName, Object value);

	public abstract T findOne(String[] paramNames, Object[] values);

	public abstract T findOne(Map<String, Object> params);
	
	public abstract T findById(Long id);

}
