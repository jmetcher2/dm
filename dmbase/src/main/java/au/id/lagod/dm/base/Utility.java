package au.id.lagod.dm.base;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.beanutils.PropertyUtils;

public class Utility {

	private Utility() {
		
	}
	
	public static Map<String,Object> arraysToMap(String[] paramNames, Object[] values) {
		Map<String,Object> params = new HashMap<String,Object>();
		for (int i = 0; i < paramNames.length; i++) {
			params.put(paramNames[i], values[i]);
		}
		return params;
	}
	
	public static <T> String printMessages(Set<ConstraintViolation<T>> violations) {
		String ret = "";
		for (ConstraintViolation<T> v: violations) {
			ret = ret.concat(v.getPropertyPath() + ":" + v.getMessage() + " | ");
		}
		return ret;
	}
	
	public static String printMessages(javax.validation.ConstraintViolationException e) {
		Set<ConstraintViolation<BaseDomainObject>> cvs = new HashSet<ConstraintViolation<BaseDomainObject>>(0);
		cvs.addAll((Collection<? extends ConstraintViolation<BaseDomainObject>>) e.getConstraintViolations());
		return e.getMessage().concat(" " + printMessages(cvs));
	}

	public static String getTextKeyField(Class clazz) {
		for (Field field: clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(TextKey.class)) {
				return field.getName();
			}
		}

		if (clazz.getSuperclass() != Object.class) {
			return getTextKeyField(clazz.getSuperclass());
		}
		else {
			return null;
		}
	}
	
	public static  String getTextIDValue(BaseDomainObject o, String keyFieldName) {
		try {
			if (keyFieldName == null) {
				return null;
			}
			else {
				return (String) PropertyUtils.getProperty(o, keyFieldName);
			}
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	


}
