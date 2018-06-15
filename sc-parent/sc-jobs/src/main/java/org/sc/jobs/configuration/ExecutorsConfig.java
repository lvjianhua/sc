package org.sc.jobs.configuration;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@org.springframework.context.annotation.Configuration
public class ExecutorsConfig {

    @Bean(name = "taskExecutor", destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }

}
