package org.sc.common.dao.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.transform.Transformers;
import org.sc.common.model.vo.Page;
import org.sc.common.service.BaseLogicService;
import org.sc.common.service.BaseService;
import org.sc.common.dao.utils.MyAliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;
import org.sc.common.model.vo.BaseEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseLogicServiceImpl<T> extends BaseService implements BaseLogicService<T> {
	protected Logger logger = LogManager.getLogger(getClass());

	private JpaRepository<T, String> jpaRepository;

	public BaseLogicServiceImpl(JpaRepository<T, String> jpa) {
		this.jpaRepository = jpa;
		/*
		 * InvocationHandler invocationHandler = Proxy.getInvocationHandler(jpa);
		 * AdvisedSupport advised = (AdvisedSupport) new
		 * DirectFieldAccessor(invocationHandler) .getPropertyValue("advised");
		 * logger.info(advised.getTargetClass() + " is started!");
		 */
		Class clz = this.getClass();
		ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		cls = (Class<T>) types[0];
	}

	@Autowired
	EntityManager entityManagerPrimary;

	@Override
	public T getById(String id) {
		return jpaRepository.findOne(id);
	}

	private Class<T> cls = null;

	@Override
	public T update(T object) {
		String id = ((BaseEntity) object).getId();

		if (StringUtils.isEmpty(id)) {
			return null;
		} else {
			T tmpObj = jpaRepository.findOne(id);
			if (StringUtils.isEmpty(tmpObj)) {
				return null;
			} else {
				mapper.map(object, tmpObj);
				jpaRepository.saveAndFlush(tmpObj);

				return tmpObj;
			}
		}
	}

	@Override
	public T save(T object) {
		String id = ((BaseEntity) object).getId();
		if (StringUtils.isEmpty(id)) {
			return jpaRepository.save(object);
		} else {
			return jpaRepository.findOne(id);
		}
	}

	/***
	 * 查询单个
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected T queryOne(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		T result = (T) query.setResultTransformer(Transformers.aliasToBean(cls)).uniqueResult();
		seesion.flush();
		return result;
	}

	protected Object queryOneObject(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		Object result = query.uniqueResult();
		seesion.flush();
		return result;
	}

	/***
	 * 查询单个
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected Integer count(String sql, Map<String, Object> params) {
		// Session seesion = entityManagerPrimary.unwrap(Session.class);

		javax.persistence.Query query = entityManagerPrimary.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return ((Number) query.getSingleResult()).intValue();
	}

	/***
	 * 查询列表
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<T> list(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		List<T> list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		seesion.clear();
		return list;
	}

	protected List listObject(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		List list = query.list();
		seesion.clear();
		return list;
	}

	protected List listNativeObject(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		List list = query.list();
		seesion.clear();
		return list;
	}

	protected List<T> listNative(String sql, Map<String, Object> params) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		List list = (List<T>) query.setResultTransformer(new MyAliasToBeanResultTransformer(cls)).list();
		seesion.clear();
		return list;
	}

	protected List<T> list(String sql, Map<String, Object> params, boolean isAlias) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}

		List<T> list = null;
		if (isAlias) {
			list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		} else {
			list = query.list();
		}
		seesion.clear();

		return list;
	}

	/**
	 * 执行本地化sql
	 * 
	 * @param sql
	 * @return
	 */
	protected List<T> queryByNatvieSql(String sql) {
		Session session = entityManagerPrimary.unwrap(Session.class);

		SQLQuery query = session.createSQLQuery(sql);

		query.setResultTransformer(Transformers.aliasToBean(cls));

		List tmpList = query.list();

		session.clear();
		return tmpList;
	}

	/**
	 * 执行本地化sql，将对象类型转换为Map类型。
	 * 
	 * @param sql
	 * @return
	 */
	protected List<T> queryByNatvieToMapSql(String sql) {
		Session session = entityManagerPrimary.unwrap(Session.class);

		SQLQuery query = session.createSQLQuery(sql);

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List tmpList = query.list();

		session.clear();
		return tmpList;
	}

	/***
	 * 分页查询列表
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<T> list(String sql, Map<String, Object> params, Integer pageNumber, Integer pageSize) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<T> list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		seesion.clear();
		return list;
	}

	/***
	 * 分页查询列表
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<T> list(String sql, Map<String, Object> params, Integer pageNumber, Integer pageSize,
			boolean isAlias) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<T> list = null;
		if (isAlias) {
			seesion.flush();
			list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
			return list;
		}
		list = (List<T>) query.list();
		seesion.clear();
		return list;
	}

	/**
	 * 分页查询 (参数使用LinkedHashMap)
	 * 
	 * @param: @param
	 *             sql
	 * @param: @param
	 *             params {@link LinkedHashMap}
	 * @param: @param
	 *             pageNumber
	 * @param: @param
	 *             pageSize
	 * @param: @param
	 *             isAlias
	 * @param: @return
	 * @return: Page<T>
	 * @throws:
	 * @author: shawn-gfresh
	 */
	protected Page<T> pageQuery(String sql, Map<String, Object> params, Integer pageNumber, Integer pageSize,
			boolean isAlias) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) value);
				} else {
					query.setParameter(entry.getKey(), value);
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<T> list = null;
		if (isAlias) {
			seesion.flush();
			list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		}
		list = (List<T>) query.list();

		Page<T> page = new Page<>();
		page.setContent(list);
		page.setCurrentPage(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalElements(getDataTotalCount(sql, params));
		page.setTotalPage((int) Math.ceil((double) page.getTotalElements() / pageSize));

		seesion.clear();

		return page;
	}

	protected Page<T> pageQuery(String hql, String countHql, Map<String, Object> params, Integer pageNumber,
			Integer pageSize, boolean isAlias) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(hql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) value);
				} else {
					query.setParameter(entry.getKey(), value);
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<T> list = null;
		if (isAlias) {
			seesion.flush();
			list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		}
		list = (List<T>) query.list();

		Page<T> page = new Page<>();
		page.setContent(list);
		page.setCurrentPage(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalElements(getCount(countHql, params));
		page.setTotalPage((int) Math.ceil((double) page.getTotalElements() / pageSize));

		seesion.clear();

		return page;
	}

	protected Page<T> pageNativeQuery(String sql, String countSql, Map<String, Object> params, Integer pageNumber,
			Integer pageSize, Class<?> resultClass) {
		// javax.persistence.Query nativeQuery =
		// entityManagerPrimary.createNativeQuery(sql, resultClass);
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<T> list = null;

		if (resultClass == null) {
			list = (List<T>) query.setResultTransformer(new MyAliasToBeanResultTransformer(cls)).list();
		} else {
			list = (List<T>) query.setResultTransformer(new MyAliasToBeanResultTransformer(resultClass)).list();
		}

		// list = (List<T>) query.list();
		Page<T> page = new Page<>();
		page.setContent(list);
		page.setCurrentPage(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalElements(getNativeCount(countSql, params));
		page.setTotalPage((int) Math.ceil((double) page.getTotalElements() / pageSize));
		seesion.clear();
		// entityManagerPrimary.clear();
		return page;
	}

	protected Page<?> pageNativeQueryWithObject(String sql, String countSql, Map<String, Object> params,
			Integer pageNumber, Integer pageSize, Class<?> resultClass) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createSQLQuery(sql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (Collection) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
		List<?> list = null;

		if (resultClass == null) {
			list = query.list();
		} else {
			list = (List<T>) query.setResultTransformer(new MyAliasToBeanResultTransformer(resultClass)).list();
		}

		Page page = new Page<>();
		page.setContent(list);
		page.setCurrentPage(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalElements(getNativeCount(countSql, params));
		page.setTotalPage((int) Math.ceil((double) page.getTotalElements() / pageSize));
		seesion.clear();
		return page;
	}

	protected <T> List<T> nativeQuery(String sql, Map<String, Object> params, Class<T> resultClass) {
		javax.persistence.Query nativeQuery = entityManagerPrimary.createNativeQuery(sql, resultClass);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List<T> list = null;
		list = (List<T>) nativeQuery.getResultList();
		return list;
	}

	protected List<T> query(String hql, Map<String, Object> params, boolean isAlias) {
		Session seesion = entityManagerPrimary.unwrap(Session.class);
		Query query = seesion.createQuery(hql);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() instanceof List) {
					query.setParameterList(entry.getKey(), (List) entry.getValue());
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		List<T> list;
		if (isAlias) {
			seesion.flush();
			list = (List<T>) query.setResultTransformer(Transformers.aliasToBean(cls)).list();
		}
		list = (List<T>) query.list();
		seesion.clear();
		return list;
	}

	protected long getCount(String hql, Map<String, Object> params) {
		Session session = entityManagerPrimary.unwrap(Session.class);
		Query countQuery = session.createQuery(hql);

		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof List) {
					countQuery.setParameterList(entry.getKey(), (Collection) value);
				} else {
					countQuery.setParameter(entry.getKey(), value);
				}
			}
		}

		List countList = countQuery.list();
		Long totalCount = 0l;
		if (countList != null) {
			totalCount = countList.size() > 0 ? new Long(countList.get(0).toString()) : 0l;
		}

		return totalCount;
	}
	
	/**
	 * 用原生sql获取一个表的一行的指定列，只返回一个String列
	 * @param sql
	 * @param params
	 * @param colName
	 * @return
	 */
	protected String getNativeOneCol(String sql, Map<String, Object> params, String colName) {
		Session session = entityManagerPrimary.unwrap(Session.class);
		Query oneColQuery = session.createSQLQuery(sql);

		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof List) {
					oneColQuery.setParameterList(entry.getKey(), (Collection) value);
				} else {
					oneColQuery.setParameter(entry.getKey(), value);
				}
			}
		}

		List countList = oneColQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		if (countList != null && countList.size()>0) {
			Map<String, Object> map = (Map<String, Object>) countList.get(0);
			return map.get(colName).toString();
		}
		
		return "";
	}
	
	private long getNativeCount(String sql, Map<String, Object> params) {
		Session session = entityManagerPrimary.unwrap(Session.class);
		Query countQuery = session.createSQLQuery(sql);

		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value instanceof List) {
					countQuery.setParameterList(entry.getKey(), (Collection) value);
				} else {
					countQuery.setParameter(entry.getKey(), value);
				}
			}
		}

		List countList = countQuery.list();

		Long totalCount = 0l;
		if (countList != null) {
			totalCount = countList.size() > 0 ? new Long(countList.get(0).toString()) : 0l;
		}

		return totalCount;
	}

	private Long getDataTotalCount(String hql, Map<String, Object> params) {
		Session session = entityManagerPrimary.unwrap(Session.class);
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP,
				(SessionFactoryImplementor) session.getSessionFactory());
		queryTranslator.compile(Collections.EMPTY_MAP, false);
		String countHql = "select count(*) from ( " + queryTranslator.getSQLString() + " ) as tmpCount";

		/** 统计符合条件的记录数 */
		javax.persistence.Query countQuery = entityManagerPrimary.createNativeQuery(countHql);

		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				for (int i = 0; i < params.size(); i++) {
					Object obj = entry.getValue();
					if (obj instanceof Date) {
						// date 转换成string
						countQuery.setParameter(i + 1, (Date) obj, TemporalType.DATE);
					} else {
						countQuery.setParameter(i + 1, obj);
					}
				}
			}
		}

		List countList = countQuery.getResultList();
		Long totalCount = 0l;
		if (countList != null) {
			totalCount = countList.size() > 0 ? new Long(countList.get(0).toString()) : 0l;
		}

		return totalCount;
	}

}
