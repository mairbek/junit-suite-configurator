package com.github.suiteconfig;

import org.jdogma.junit.NonBlockingAsynchronousRunner;
import org.jdogma.junit.SynchronousRunner;

import java.util.concurrent.ExecutorService;

public class SynchronizedRunnerScheduler extends SynchronousRunner {
    private final NonBlockingAsynchronousRunner child;
    private boolean completed;

    public SynchronizedRunnerScheduler(ExecutorService executorService) {
        this.child = new NonBlockingAsynchronousRunner(null, executorService);
    }

    @Override
    public void finished() {
        child.waitForCompletion();
        completed = true;
    }

    public NonBlockingAsynchronousRunner getChild() {
        return child;
    }

    public boolean isCompleted() {
        return completed;
    }

}
