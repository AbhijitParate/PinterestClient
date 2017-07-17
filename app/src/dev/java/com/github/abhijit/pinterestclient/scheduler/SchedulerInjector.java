package com.github.abhijit.pinterestclient.scheduler;


public class SchedulerInjector {

    public static DevelopmentSchedulerProvider getScheduler() {
        return DevelopmentSchedulerProvider.newInstance();
    }

}