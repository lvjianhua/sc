package org.sc.api.os.service;

import java.util.List;

import org.sc.facade.os.model.table.Test;
import org.sc.common.service.BaseLogicService;

/**
 * Test接口
 * 
 * @author lvjh
 *
 */
public interface TestApiService{
	Test findByName(String name);

	List<Test> findAll();
}
