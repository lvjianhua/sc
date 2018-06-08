package org.sc.common.dao.usertype;

import org.sc.common.dao.utils.ClassCache;
import org.sc.common.utils.GenericTypeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

public class JsonbUserType implements UserType {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private ThreadLocal threadLocal = new ThreadLocal<>();
	private static final String JSONB_TYPE = "jsonb";

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			Object obj = JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
			if (obj == null) {
				st.setNull(index, Types.OTHER);
			} else {
				PGobject pgObject = new PGobject();
				pgObject.setType(JSONB_TYPE);
				pgObject.setValue(obj.toString());
				st.setObject(index, pgObject, Types.OTHER);
			}
		}
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		if (owner == null) {
			// throw new SQLException("抛出异常");
			if (names.length > 0) {
				PGobject pGobject = (PGobject) rs.getObject(names[0]);
				if (pGobject == null) {
					return null;
				}
				/** 截取select的字段 反射获取字段类型返回 */
				Map<String, Integer> indexMap = getResultSetColumnNameIndexMap(rs);

				String entityName = getSelectEntity(rs);

				String columnName = getSelectField(rs, indexMap.get(names[0]));

				if (StringUtils.isEmpty(columnName)) {
					return null;
				} else {
					Field field = ClassCache.getClassFieldByClassName(entityName.replaceAll("_", ""), columnName);
					if (ObjectUtils.isEmpty(field)) {
						return null;
					} else {
						field.setAccessible(true);
						return parseValueObj(field, pGobject);
					}
				}
			}
			return null;
		}

		PGobject pGobject = (PGobject) rs.getObject(names[0]);
		if (pGobject != null && pGobject.getValue() != null) {
			Object obj = null;
			Map<String, Integer> indexMap = getResultSetColumnNameIndexMap(rs);
			Field field = ClassCache.getClassFieldByClassName(owner.getClass().getSimpleName(), names[0]);
			if (ObjectUtils.isEmpty(field)) {
				field = ClassCache.getClassFieldByClassName(owner.getClass().getSimpleName(), indexMap.get(names[0]));
			}
			field.setAccessible(true);
			if (field != null) {
				obj = parseValueObj(field, pGobject);
			}
			if (obj == null) {
				return pGobject.getValue();
			} else {
				return obj;
			}
		}
		return null;
	}

	@Override
	public Object deepCopy(Object originalValue) throws HibernateException {
		if (originalValue == null) {
			return null;
		}
		return originalValue;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object copy = deepCopy(value);
		if (copy instanceof Serializable) {
			return (Serializable) copy;
		}
		throw new SerializationException(
				String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		if (x == null) {
			return 0;
		}
		return x.hashCode();
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}

	@Override
	public Class<?> returnedClass() {
		return Object.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.JAVA_OBJECT };
	}

	/**
	 * 解析json值
	 * 
	 * @param field
	 * @param pGobject
	 * @return
	 */
	private Object parseValueObj(Field field, PGobject pGobject) {
        String value = pGobject.getValue();
        if (value == null) {
            return null;
		}
		Object obj;
		if (field.getType().equals(List.class)) { // List类型
			Class<?>[] genericTypes = GenericTypeUtils.getGenericType(field.getGenericType());
            obj = JSON.parseArray(value, genericTypes[0]);
        } else if (field.getType().equals(Map.class)) { // Map类型
            obj = JSON.parseObject(value, field.getGenericType());
        } else {
            obj = JSON.parseObject(value, field.getType());
        }
		return obj;
	}

	/**
	 * 获取ResultSet列名与顺序Map
	 * 
	 * @return
	 */
	private Map<String, Integer> getResultSetColumnNameIndexMap(ResultSet rs) {
		Map<String, Integer> columnNameIndexMap = null;
		try {
			Field field = rs.getClass().getDeclaredField("columnNameIndexMap");
			field.setAccessible(true);
			columnNameIndexMap = (Map<String, Integer>) field.get(rs);
			return columnNameIndexMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** 获取对应的field字段 */
	private String getSelectField(ResultSet rs, int index) {
		try {
			String select = rs.getStatement().toString();

			select = select.substring(6, select.indexOf("from "));

			String[] selects = select.split(",");

			if (selects.length < index) {
				return null;
			} else {
				String tmpField = selects[index - 1].trim();
				return tmpField.substring(tmpField.indexOf(".") + 1, tmpField.indexOf(" "));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getSelectEntity(ResultSet rs) {
		try {
			String select = rs.getStatement().toString();

			if (select.indexOf("where ") != -1) {
				select = select.substring(select.indexOf("from ") + 5, select.indexOf("where "));
			}


			return select.substring(0, select.indexOf(" ")).replaceAll("\"", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
