package com.github.suiteconfig;

import org.jdogma.junit.AsynchronousRunner;
import org.jdogma.junit.NonBlockingAsynchronousRunner;
import org.jdogma.junit.SynchronousRunner;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;

public class Schedulers {
    private static final SynchronousRunner SYNCHRONOUS_RUNNER = new SynchronousRunner();

    private Schedulers() {

    }

    public static RunnerScheduler singleThread() {
        return SYNCHRONOUS_RUNNER;
    }

    public static AsynchronousRunner asynchronous(ExecutorService executorService) {
        return new AsynchronousRunner(null, executorService);
    }

    public static NonBlockingAsynchronousRunner nonBlocking(ExecutorService executorService) {
        return new NonBlockingAsynchronousRunner(null, executorService);
    }

}
