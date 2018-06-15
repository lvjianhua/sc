package org.sc.jobs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * configuration
 * Created by simon on 2017/4/7.
 */

@EnableScheduling
public class TasksConfigure implements SchedulingConfigurer {

    @Autowired
    protected Executor taskExecutor;

    @Override
    //ScheduledTaskRegistrar类就是整个spring配置里的定时任务的注册中心
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor);
    }


}
