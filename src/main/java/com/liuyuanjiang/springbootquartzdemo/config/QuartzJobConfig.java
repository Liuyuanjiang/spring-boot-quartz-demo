package com.liuyuanjiang.springbootquartzdemo.config;

import com.liuyuanjiang.springbootquartzdemo.schedule.TestJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.Map;
import java.util.TimeZone;

@Configuration
@ConfigurationProperties(prefix = "quartz")
public class QuartzJobConfig {

 private Map<String,JobConfig> jobConfig;

    public Map<String, JobConfig> getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(Map<String, JobConfig> jobConfig) {
        this.jobConfig = jobConfig;
    }

    public static class JobConfig{
        private String triggerCronExpression;
        private String triggerTimezone;

        public String getTriggerCronExpression() {
            return triggerCronExpression;
        }

        public void setTriggerCronExpression(final String triggerCronExpression) {
            this.triggerCronExpression = triggerCronExpression;
        }

        public String getTriggerTimezone() {
            return triggerTimezone;
        }

        public void setTriggerTimezone(final String triggerTimezone) {
            this.triggerTimezone = triggerTimezone;
        }
    }

    @Bean
    public JobDetailFactoryBean testJobDetailFactory(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(TestJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("testJob");
        return factoryBean;
    }

    @Bean
    public JobDetail testJobDetail(){
        return testJobDetailFactory().getObject();
    }

    @Bean
    @ConditionalOnProperty(prefix = "quartz.jobConfig.testJob", name = "triggerCronExpression")
    public CronTriggerFactoryBean testJobTriggerFactory(){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(testJobDetail());
        factoryBean.setCronExpression(this.jobConfig.get("testJob").getTriggerCronExpression());
        factoryBean.setTimeZone(TimeZone.getTimeZone(this.jobConfig.get("testJob").getTriggerTimezone()));
        factoryBean.setName("testJobTrigger");
        return factoryBean;
    }

    @Bean
    @ConditionalOnProperty(name = "testJobTriggerFactory")
    public Trigger testJobTrigger(){
        CronTrigger trigger = testJobTriggerFactory().getObject();
        return trigger;
    }


}
