package org.sc.client;

import org.sc.configuration.Response;
import org.sc.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by admin on 2017/5/10.
 */
@FeignClient(value = "ps-service")
public interface UserClient {
	@RequestMapping(value = "/user/login", method = { RequestMethod.POST })
	Object login(@RequestBody User User);
	
	@RequestMapping(method = RequestMethod.GET, value = "/role/getByUserId")
    public Response getByUserId(@RequestParam(value = "userId") String userId);
}
