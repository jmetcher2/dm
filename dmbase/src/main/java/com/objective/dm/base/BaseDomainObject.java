package com.objective.dm.base;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;

import com.objective.dm.validators.Restricted;

public abstract class BaseDomainObject {

	private DomainObjectCollectionManager<? extends BaseDomainObject> parentManager;

	public BaseDomainObject() {
		super();
	}
	
	public DomainObjectCollectionManager<? extends BaseDomainObject> getParentManager() {
		return parentManager;
	}

	@Restricted
	public void setParentManager(DomainObjectCollectionManager<? extends BaseDomainObject> parentManager) {
		this.parentManager = parentManager;
	}
	
	
	public int compareTo(BaseDomainObject o) {
		return 0;
	}
	
	public abstract Long getId();
	
	// Note: if this reflection-based approach proves to be too slow, we can
	// a) cache the value after the first call, or
	// b) override these two methods in subclasses 
	public String getTextKeyField() {
		return getTextKeyField(this.getClass());
	}
	public boolean hasTextKey() {
		return getTextKeyField() != null;
	}
	
	public String getTextKey() {
		String keyFieldName = getTextKeyField();
		try {
			if (keyFieldName == null) {
				return null;
			}
			else {
				return (String) PropertyUtils.getProperty(this, keyFieldName);
			}
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Remove associated objects
	 * 
	 * We call this before removing an object from its aggregate, which is effectively deletion.
	 * This method must disconnect the object from any associations with objects outside the aggregate.
	 * Note that composed child objects must also be disconnected
	 * 
	 * Default is to do nothing.  Override this method if the object or its children have associates.
	 */
	public void removeAssociates() {
	}
	
	public String getMessage() {
		return " BaseDomainObject = "+this.getClass()+"  Text Key Field = "+this.getTextKeyField()+" Text Key = "+ this.getTextKey();
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
	
	public static final String collectionAccessor = "com.objective.dm.persistence.collectiongetter.PropertyAccessStrategyCollectionImpl";
	

}