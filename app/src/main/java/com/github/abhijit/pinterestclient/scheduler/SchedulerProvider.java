package com.github.abhijit.pinterestclient.scheduler;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler computation();
    Scheduler io();
    Scheduler ui();
}
