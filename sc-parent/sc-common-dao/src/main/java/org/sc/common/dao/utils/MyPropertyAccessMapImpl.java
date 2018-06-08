package org.sc.common.dao.utils;

import java.lang.reflect.Field;

import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.GetterFieldImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterFieldImpl;
import org.springframework.util.ObjectUtils;


/**
 * PropertyAccess implementation that deal with an underlying Map as the
 * container using {@link Map#get} and {@link Map#put}
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
public class MyPropertyAccessMapImpl implements PropertyAccess {
	private final Getter getter;
	private final Setter setter;
	private final MyPropertyAccessStrategyImpl strategy;

	public MyPropertyAccessMapImpl(MyPropertyAccessStrategyImpl strategy, Class containerJavaType,
			final String propertyName) {
		this.strategy = strategy;

		final Field field1 = ClassCache.getClassFieldByClassName(containerJavaType.getSimpleName(), propertyName);
		if (!ObjectUtils.isEmpty(field1)) {
			field1.setAccessible(true);
			this.getter = new GetterFieldImpl(containerJavaType, propertyName, field1);
			this.setter = new MySetterFieldImpl(containerJavaType, propertyName, field1);
		} else {
			final Field field = ReflectHelper.findField(containerJavaType, propertyName);
			this.getter = new GetterFieldImpl(containerJavaType, propertyName, field);
			this.setter = new SetterFieldImpl(containerJavaType, propertyName, field);
		}
	}

	@Override
	public Getter getGetter() {
		return getter;
	}

	@Override
	public Setter getSetter() {
		return setter;
	}

	@Override
	public PropertyAccessStrategy getPropertyAccessStrategy() {
		return strategy;
	}
}

