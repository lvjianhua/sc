package org.sc.conAndRefresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RefreshScope// 不重启服务的情况下刷新配置
public class ConfigController {

	@Autowired
	private Environment env;
	
	@RequestMapping(value = "/gp1", method = RequestMethod.GET)
	public String getProp1() {
		return env.getProperty("test.user.name");
	}
	
	@RequestMapping(value = "/gp2", method = RequestMethod.GET)
	public String getProp2() {
		return env.getProperty("test.user.passwork");
	}
}
