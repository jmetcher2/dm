package com.objective.dm.persistence.collectiongetter;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.property.access.internal.AbstractFieldSerialForm;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccessException;

import au.id.lagod.dm.base.DomainObjectCollectionManager;


/**
 * Implementation of Getter for delegated collections in CollectManagers
 *
 */
public class GetterCollectionImpl implements Getter {
	private final Class containerClass;
	private final String propertyName;
	private final Field field;

	public GetterCollectionImpl(Class containerClass, String propertyName, Field field) {
		this.containerClass = containerClass;
		this.propertyName = propertyName;
		this.field = field;
	}

	@Override
	public Object get(Object owner) {
		try {
			DomainObjectCollectionManager manager = (DomainObjectCollectionManager) field.get(owner);
			return manager.getCollection();
		}
		catch (Exception e) {
			throw new PropertyAccessException(
					String.format(
							Locale.ROOT,
							"Error accessing field [%s] by reflection for persistent property [%s#%s] : %s",
							field.toGenericString(),
							containerClass.getName(),
							propertyName,
							owner
					),
					e
			);
		}
	}

	@Override
	public Object getForInsert(Object owner, Map mergeMap, SharedSessionContractImplementor session) {
		return get( owner );
	}

	@Override
	public Class getReturnType() {
		return field.getType();
	}

	@Override
	public Member getMember() {
		return field;
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
			return new GetterCollectionImpl( containerClass, propertyName, resolveField() );
		}
	}
}

