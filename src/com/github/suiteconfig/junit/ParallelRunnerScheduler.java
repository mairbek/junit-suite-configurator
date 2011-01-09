package com.github.suiteconfig.junit;

import com.google.common.collect.Lists;
import org.junit.runners.model.RunnerScheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ParallelRunnerScheduler implements RunnerScheduler {
    private final ExecutorService executorService;
    private List<Future<Void>> tasks = Lists.newLinkedList();

    public ParallelRunnerScheduler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void schedule(final Runnable childStatement) {
        tasks.add(executorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                childStatement.run();
                return null;
            }
        }));

    }

    public void finished() {
        for (Future<Void> task : tasks) {
            try {
                task.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
