package org.sc.common.utils;



import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.PropertyAccessException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.AbstractFieldSerialForm;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.property.access.spi.SetterFieldImpl;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;

public class MySetterFieldImpl implements Setter {
	private final Class containerClass;
	private final String propertyName;
	private final Field field;

	public MySetterFieldImpl(Class containerClass, String propertyName, Field field) {
		this.containerClass = containerClass;
		this.propertyName = propertyName;
		this.field = field;
	}

	@Override
	public void set(Object target, Object value, SessionFactoryImplementor factory) {
		try {
			if (field.getType().getName().contains("cn.gfresh") || field.getType().getName().contains("java.util.List")
					|| field.getType().getName().contains("java.util.Map")
					|| field.getType().getName().contains("java.util.HashMap")) {
				if (ObjectUtils.isEmpty(value)) {
					field.set(target, value);
				} else {
					Object obj;
					if (field.getType().equals(List.class)) { // List类型
						Class<?>[] genericTypes = GenericTypeUtils.getGenericType(field.getGenericType());
						obj = JSON.parseArray(value.toString(), genericTypes[0]);
					} else if (field.getType().equals(Map.class)) { // Map类型
						obj = JSON.parseObject(value.toString(), field.getGenericType());
					} else {
						obj = JSON.parseObject(value.toString(), field.getType());
					}
					field.set(target, obj);
				}
			} else {
				field.set(target, value);
			}
		} catch (Exception e) {
			if (value == null && field.getType().isPrimitive()) {
				throw new PropertyAccessException(e,
						String.format(Locale.ROOT, "Null value was assigned to a property [%s.%s] of primitive type",
								containerClass, propertyName),
						true, containerClass, propertyName);
			} else {
				throw new PropertyAccessException(e,
						String.format(Locale.ROOT, "Could not set field value [%s] value by reflection : [%s.%s]",
								value, containerClass, propertyName),
						true, containerClass, propertyName);
			}
		}
	}

	@Override
	public String getMethodName() {
		return field.getType().getSimpleName();
	}

	@Override
	public Method getMethod() {
		return null;
	}

	private Object writeReplace() throws ObjectStreamException {
		return new SerialForm(containerClass, propertyName, field);
	}

	private static class SerialForm extends AbstractFieldSerialForm implements Serializable {
		private final Class containerClass;
		private final String propertyName;

		private SerialForm(Class containerClass, String propertyName, Field field) {
			super(field);
			this.containerClass = containerClass;
			this.propertyName = propertyName;
		}

		private Object readResolve() {
			return new SetterFieldImpl(containerClass, propertyName, resolveField());
		}
	}
}
