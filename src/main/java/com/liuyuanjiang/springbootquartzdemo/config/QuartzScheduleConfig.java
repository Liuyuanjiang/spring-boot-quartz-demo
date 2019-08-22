package com.liuyuanjiang.springbootquartzdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzScheduleConfig {

    @Autowired
    private QuartzAutoConfiguration quartz;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public SchedulerFactoryBean quartzScheduler(){
        SchedulerFactoryBean schedulerFactoryBean = quartz.quartzScheduler();
        schedulerFactoryBean.setDataSource(this.dataSource);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setStartupDelay(10);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        return schedulerFactoryBean;
    }
}
