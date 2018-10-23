package org.sc.api.os.service.impl;

import java.util.List;

import org.sc.facade.os.model.table.Test;
import org.sc.facade.os.service.TestService;
import org.sc.api.os.service.TestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestApiService {
	@Autowired
	private TestService testService;

	@Override
	public Test findByName(String name) {
		return testService.findByName(name);
	}

	@Override
	public List<Test> findAll() {
		return testService.findAll();
	}

}
