package com.objective.dm.persistence.collectiongetter;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;

import org.hibernate.PropertyAccessException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.AbstractFieldSerialForm;
import org.hibernate.property.access.spi.Setter;

import com.objective.dm.base.DomainCollectionManager;

/**
 * Field-based implementation of Setter
 *
 * @author Steve Ebersole
 */
public class SetterCollectionImpl implements Setter {
	private final Class containerClass;
	private final String propertyName;
	private final Field field;

	public SetterCollectionImpl(Class containerClass, String propertyName, Field field) {
		this.containerClass = containerClass;
		this.propertyName = propertyName;
		this.field = field;
	}

	@Override
	public void set(Object target, Object value, SessionFactoryImplementor factory) {
		try {
			DomainCollectionManager manager = (DomainCollectionManager) field.get(target);
			manager.setCollection((Collection) value);
		}
		catch (Exception e) {
			if (value == null && field.getType().isPrimitive()) {
				throw new PropertyAccessException(
						e,
						String.format(
								Locale.ROOT,
								"Null value was assigned to a property [%s.%s] of primitive type",
								containerClass,
								propertyName
						),
						true,
						containerClass,
						propertyName
				);
			}
			else {
				throw new PropertyAccessException(
						e,
						String.format(
								Locale.ROOT,
								"Could not set field value [%s] value by reflection : [%s.%s]",
								value,
								containerClass,
								propertyName
						),
						true,
						containerClass,
						propertyName
				);
			}
		}
	}

	@Override
	public String getMethodName() {
		return null;
	}

	@Override
	public Method getMethod() {
		return null;
	}

	private Object writeReplace() throws ObjectStreamException {
		return new SerialForm( containerClass, propertyName, field );
	}

	private static class SerialForm extends AbstractFieldSerialForm implements Serializable {
		private final Class containerClass;
		private final String propertyName;

		private SerialForm(Class containerClass, String propertyName, Field field) {
			super( field );
			this.containerClass = containerClass;
			this.propertyName = propertyName;
		}

		private Object readResolve() {
			return new SetterCollectionImpl( containerClass, propertyName, resolveField() );
		}
	}
}
