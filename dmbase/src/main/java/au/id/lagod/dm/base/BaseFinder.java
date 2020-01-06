package au.id.lagod.dm.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jmetcher
 *
 * @param <T>
 * 
 * Abstraction of the fact that various ways of specifying name/value pairs
 * to be found can be translated into a single form (public List<T> find(Map<String, Object> params);)
 * This class provides those translations.
 * 
 */
public abstract class BaseFinder<T> implements Finder<T>  {

	public BaseFinder() {}

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#find(java.util.Map)
	 */
	public abstract List<T> find(Map<String, Object> params);

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#findAll()
	 */
	public abstract List<T> findAll();

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#find(java.lang.String[], java.lang.Object[])
	 */
	public List<T> find(String[] paramNames, Object[] values) {
		Map<String,Object> params = Utility.arraysToMap(paramNames,values);
		
		return find(params);
	}

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#find(java.lang.String, java.lang.Object)
	 */
	public List<T> find(String paramName, Object value) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(paramName, value);
		
		return find(params);
	}

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#findOne(java.lang.String, java.lang.Object)
	 */
	public T findOne(String paramName, Object value) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(paramName, value);
		
		return findOne(params);
	}

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#findOne(java.lang.String[], java.lang.Object[])
	 */
	public T findOne(String[] paramNames, Object[] values) {
		Map<String,Object> params = Utility.arraysToMap(paramNames,values);
		
		return findOne(params);
	}

	/* (non-Javadoc)
	 * @see com.medeserv.Finder#findOne(java.util.Map)
	 */
	public T findOne(Map<String, Object> params) {
		List<T> results = find(params);
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}
	
	public T findbyId(Long id) {
		return findOne("id", id);
	}

}
