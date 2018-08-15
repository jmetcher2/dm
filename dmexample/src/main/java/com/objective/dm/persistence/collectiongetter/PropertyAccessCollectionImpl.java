package com.objective.dm.persistence.collectiongetter;

import java.lang.reflect.Field;

import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.GetterFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterFieldImpl;

public class PropertyAccessCollectionImpl implements PropertyAccess {
	private final PropertyAccessStrategyCollectionImpl strategy;
	private final Getter getter;
	private final Setter setter;

	public PropertyAccessCollectionImpl(
			PropertyAccessStrategyCollectionImpl strategy,
			Class containerJavaType,
			final String propertyName) {
		this.strategy = strategy;

		final Field field = ReflectHelper.findField( containerJavaType, propertyName );
		this.getter = new GetterCollectionImpl( containerJavaType, propertyName, field );
		this.setter = new SetterCollectionImpl( containerJavaType, propertyName, field );
	}

	@Override
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return strategy;
	}

	@Override
	public Getter getGetter() {
		return getter;
	}

	@Override
	public Setter getSetter() {
		return setter;
	}

}
