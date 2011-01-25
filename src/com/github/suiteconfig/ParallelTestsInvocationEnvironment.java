package com.github.suiteconfig;

import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;

public class ParallelTestsInvocationEnvironment implements InvocationEnvironment {
    private final ExecutorService executorService;
    private final Object LOCK = new Object();

    private SynchronizedRunnerScheduler classesScheduler;

    public ParallelTestsInvocationEnvironment(ExecutorService executorService) {
        this.executorService = executorService;
        this.classesScheduler = new SynchronizedRunnerScheduler(executorService);
    }

    public RunnerScheduler testScheduler() {
        synchronized (LOCK) {
            return classesScheduler.getChild();
        }
    }

    public RunnerScheduler classesScheduler() {
        synchronized (LOCK) {
            if (classesScheduler.isCompleted()) {
                this.classesScheduler = new SynchronizedRunnerScheduler(executorService);
            }
            return classesScheduler;
        }
    }
}
