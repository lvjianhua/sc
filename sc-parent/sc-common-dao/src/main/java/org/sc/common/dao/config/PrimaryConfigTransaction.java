package org.sc.common.dao.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryPrimary",
        transactionManagerRef="transactionManagerPrimary",
        basePackages= {"org.sc.service.*.dao","org.sc.common.dao.dao"}) //设置Repository所在位置

public class PrimaryConfigTransaction {


    @Autowired @Qualifier("entityManagerFactoryPrimary")
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary;


    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager() {
        return entityManagerFactoryPrimary.getObject().createEntityManager();
    }


    @Bean(name = "transactionManagerPrimary")
    PlatformTransactionManager transactionManagerPrimary() {
        return new JpaTransactionManager(entityManagerFactoryPrimary.getObject());
    }
}
