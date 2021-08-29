package au.id.lagod.dm.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

	// TODO: this is almost identical code to au.id.lagod.dm.base.DomainCollectionManager.flatten(List<String>, List<S>)
	@SuppressWarnings("unchecked")
	public <S extends BaseDomainObject> void flatten(List<String> names, List<S> values) {
		String name = names.get(0);
		try {
			Object value;
			value = PropertyUtils.getSimpleProperty(this, name);
			if (value instanceof DomainObjectCollectionManager) {
				List<String> nextLevelNames = new ArrayList<String>(names);
				nextLevelNames.remove(0);
				if (nextLevelNames.size() == 0) {
					values.addAll((DomainObjectCollectionManager<S>) value);
				}
				else {
					((DomainObjectCollectionManager<S>) value).flatten(nextLevelNames, values);
				}
			}
			else if (value instanceof BaseDomainObject) {
				List<String> nextLevelNames = new ArrayList<String>(names);
				nextLevelNames.remove(0);
				if (nextLevelNames.size() == 0) {
					values.add((S) value);
				}
				else {
					((S) value).flatten(nextLevelNames, values);
				}
			}
			else {
				// TODO: Here we'd want to continue down the name list until we reach another DomainObjectCollectionManager
				throw new java.lang.Error ("Not handling this case yet");
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}