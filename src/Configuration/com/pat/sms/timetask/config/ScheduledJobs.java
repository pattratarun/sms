package com.pat.sms.timetask.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pat.sms.job.ScheduledJob;

@Configuration
@EnableScheduling
public class ScheduledJobs {
    @Bean
    @ConditionalOnProperty(value = "jobs.enabled", matchIfMissing = true, havingValue = "true")
    public ScheduledJob scheduledJob() {
        return new ScheduledJob();
    }
}
