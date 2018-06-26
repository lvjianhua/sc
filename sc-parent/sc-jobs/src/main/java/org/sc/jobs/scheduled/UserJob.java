package org.sc.jobs.scheduled;

import org.sc.facade.ps.model.table.User;
import org.sc.jobs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用户jobs
 * Created by Webb lv on 2017/9/15.
 */
@Component
public class UserJob {

    @Autowired
    private UserService userService;

    /**
     * 每隔1分钟跑一次<br/>
     * 查询用户的信息<br/>
     * @throws Exception
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void findByUserName() throws Exception {
    	User user = userService.findByUserName("楚留香");
    	System.out.println(user);
    }

}
