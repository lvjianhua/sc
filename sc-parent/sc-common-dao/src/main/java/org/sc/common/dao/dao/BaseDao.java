package org.sc.common.dao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 
 * 执行本地化sql  
 * @author: shawn-gfresh  create by:2017年4月13日 下午7:05:14 
 *   
 * @version V1.0
 */
@Service
public class BaseDao{

	private static final Logger LOG = LogManager.getLogger(BaseDao.class);

	@PersistenceContext(unitName="primaryPersistenceUnit")
	private EntityManager em;

	/**
	 * 
	 * @Description: 执行本地化sql 返回结果集 
	 * @param: @param sql
	 * @param: @return      
	 * @return: Object      
	 * @throws:
	 * @author: shawn-gfresh
	 */
	public Object findBySql(String sql){
		Query query=em.createNativeQuery(sql);
		
		return query.getResultList();
	}
}
