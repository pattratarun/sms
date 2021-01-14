package com.pat.sms.job;

import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledJob {
    @Scheduled(fixedDelay = 60000)
    public void cleanTempDir() {
        // do work here
  }
}
