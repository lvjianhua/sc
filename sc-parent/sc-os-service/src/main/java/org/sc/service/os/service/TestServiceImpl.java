package org.sc.service.os.service;

import java.util.List;

import javax.annotation.Resource;

import org.sc.facade.os.model.table.Test;
import org.sc.facade.os.service.TestService;
import org.sc.service.os.dao.primary.TestPrimaryDao;
import org.sc.service.os.dao.secondary.TestSecondaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService{
	@Autowired
	private TestPrimaryDao testPrimaryDao; 
	
	@Autowired
	private TestSecondaryDao testSecondaryDao; 
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Value("${server.port}")
	String serverPort;
	
	@Override
	public Test findByName(String name) {
		System.out.print("请求端口号:"+serverPort);
		return testPrimaryDao.findByName(name);
	}
	@Override
	public List<Test> findAll() {
		return testSecondaryDao.findAll();
	}
	@Override
	public Test saveTest(Test test) {
		return testPrimaryDao.save(test);
	}
	
	@Override
	public Test updateTest(Test test) {
		Query q=new Query(new Criteria("id").is(test.getId()));
		Update update = new Update();
		update.set("name", test.getName());
		update.set("age", test.getAge());
		update.set("sex", test.getSex());
		update.set("add", test.getAdd());
		mongoTemplate.updateMulti(q, update,Test.class);
		return mongoTemplate.findById(test.getId(), Test.class);
	}
	@Override
	public String removeById(String id) {
		Criteria criteria = Criteria.where("id").is(id);
		Query query = new Query(criteria);
		mongoTemplate.remove(query,Test.class);
		return "ok";
	}
	@Override
	public Test findById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query,Test.class);
	}
	@Override
	public List<Test> pageTest(int pageInde, int pageSize) {
		Query query = new Query();
	    query.skip((pageInde - 1) * pageSize);
	    query.limit(pageSize);
	    query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "id")));//按照userId排序
	    List<Test> users = mongoTemplate.find(query,Test.class);
	    return users;
	}



}
