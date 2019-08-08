package au.id.lagod.dm.base;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import au.id.lagod.dm.validators.Restricted;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public abstract class BaseDomainObject {

	// TODO: do we need this field?  If yes, need to make sure it's always set
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
			} else {
				return (String) PropertyUtils.getProperty(this, keyFieldName);
			}
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * Remove associated objects
	 * 
	 * We call this before removing an object from its aggregate, which is
	 * effectively deletion. This method must disconnect the object from any
	 * associations with objects outside the aggregate. Note that composed child
	 * objects must also be disconnected
	 * 
	 * Default is to do nothing. Override this method if the object or its children
	 * have associates.
	 */
	public void removeAssociates() {
	}

	public String getMessage() {
		return " BaseDomainObject = " + this.getClass() + "  Text Key Field = " + this.getTextKeyField()
				+ " Text Key = " + this.getTextKey();
	}

	public static String getTextKeyField(Class clazz) {
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(TextKey.class)) {
					return field.getName();
				}
			}
		} catch (SecurityException e) {
			return null;
		}

		if (clazz.getSuperclass() != Object.class) {
			return getTextKeyField(clazz.getSuperclass());
		} else {
			return null;
		}
	}

}