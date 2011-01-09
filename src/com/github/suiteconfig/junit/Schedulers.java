package com.github.suiteconfig.junit;

import org.junit.runners.model.RunnerScheduler;

public class Schedulers {
    private Schedulers() {

    }

    public static RunnerScheduler singleThread() {
        return new RunnerScheduler() {
            public void schedule(Runnable childStatement) {
                childStatement.run();
            }

            public void finished() {
            }
        };
    }
}
