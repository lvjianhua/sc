package org.sc.common.dao.utils;

import java.util.Arrays;

import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.sc.common.utils.StringUtils;
import org.springframework.util.ObjectUtils;


/**
 * 
 * @author: lvjh
 * 
 * @version V1.0
 */
public class MyAliasToBeanResultTransformer extends AliasToBeanResultTransformer {

	// IMPL NOTE : due to the delayed population of setters (setters cached
	// for performance), we really cannot properly define equality for
	// this transformer

	private final Class resultClass;
	private boolean isInitialized;
	private String[] aliases;
	private Setter[] setters;

	public MyAliasToBeanResultTransformer(Class resultClass) {
		super(resultClass);
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		} else {
			isInitialized = false;
			this.resultClass = resultClass;
			return;
		}
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		try {
			if (!isInitialized) {
				initialize(aliases);
			} else {
				check(aliases);
			}

			result = resultClass.newInstance();

			for (int i = 0; i < aliases.length; i++) {
				if (setters[i] != null) {
					if (!ObjectUtils.isEmpty(tuple[i]) && !StringUtils.isEmpty(setters[i].getMethodName())) {
						if (setters[i].getMethodName().endsWith("Double")) {
							tuple[i] = Double.valueOf(tuple[i] + "");
						}
						if (setters[i].getMethodName().endsWith("Long")) {
							tuple[i] = Long.valueOf(tuple[i] + "");
						}
						if (setters[i].getMethodName().endsWith("Float")) {
							tuple[i] = Float.valueOf(tuple[i] + "");
						}
					}
					setters[i].set(result, tuple[i], null);
				}
			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}

		return result;
	}

	private void initialize(String[] aliases) {
		PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(
				MyPropertyAccessStrategyImpl.INSTANCE);
		this.aliases = new String[aliases.length];
		setters = new Setter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			// 把下划线字段转换为下划线加首字母大写
			if (alias.contains("_")) {
				if (alias.endsWith("_")) {
					alias = alias.substring(0, alias.length() - 1);
				}
				while (alias.indexOf("_") > 0) {
					int pos = alias.indexOf("_");
					String tmpStr = alias.substring(pos + 1, pos + 2);
					alias = alias.replaceAll("_" + tmpStr, tmpStr.toUpperCase());
				}
			}
			if (alias != null) {
				this.aliases[i] = alias;
				setters[i] = propertyAccessStrategy.buildPropertyAccess(resultClass, alias).getSetter();
			}
		}
		isInitialized = true;
	}

	private void check(String[] aliases) {
		if (aliases.length != this.aliases.length) {
			throw new IllegalStateException("aliases are different from what is cached; aliases="
					+ Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
		}
		
		for (int i = 0, len = aliases.length; i < len; i++) {
			if(!aliases[i].replaceAll("_", "").toLowerCase().equals(this.aliases[i].replaceAll("_", "").toLowerCase())) {
				throw new IllegalStateException("aliases are different from what is cached; aliases="
						+ Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MyAliasToBeanResultTransformer that = (MyAliasToBeanResultTransformer) o;

		if (!resultClass.equals(that.resultClass)) {
			return false;
		}
		if (!Arrays.equals(aliases, that.aliases)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = resultClass.hashCode();
		result = 31 * result + (aliases != null ? Arrays.hashCode(aliases) : 0);
		return result;
	}
}
