package com.objective.dm.persistence.collectiongetter;

import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;

public class PropertyAccessStrategyCollectionImpl implements PropertyAccessStrategy{
	/** 
	 * Defines a strategy for accessing the delegated collection within a CollectionManager.
	 * Based on Hibernates org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl
	 *
	 */

	/**
	 * Singleton access
	 */
	public static final PropertyAccessStrategyCollectionImpl INSTANCE = new PropertyAccessStrategyCollectionImpl();

	@Override
	public PropertyAccess buildPropertyAccess(Class containerJavaType, String propertyName) {
		return new PropertyAccessCollectionImpl( this, containerJavaType, propertyName );
	}

}
