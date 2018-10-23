package org.sc.service.os.dao.secondary;

import org.sc.facade.os.model.table.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
/**
 * Test对应的Secondary数据源接口
 * 
 * @author lvjh
 *
 */
public interface TestSecondaryDao extends MongoRepository<Test, String> {
    /**
     * 按名称查询
     * 
     * @param name
     * @return
     */
	public Test findByName(@Param(value="name")String name);


}
