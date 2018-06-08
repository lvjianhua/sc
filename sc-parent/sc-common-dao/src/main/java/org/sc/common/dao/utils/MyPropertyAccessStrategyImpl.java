package org.sc.common.dao.utils;

import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;

public class MyPropertyAccessStrategyImpl implements PropertyAccessStrategy {

	public static final MyPropertyAccessStrategyImpl INSTANCE = new MyPropertyAccessStrategyImpl();

	@Override
	public PropertyAccess buildPropertyAccess(Class containerJavaType, String propertyName) {
		return new MyPropertyAccessMapImpl(this, containerJavaType, propertyName);
	}
}
