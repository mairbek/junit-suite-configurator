package com.github.suiteconfig;

import com.github.suiteconfig.junit.ParallelRunnerScheduler;
import com.github.suiteconfig.junit.Schedulers;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;

public class TestExecutors {
    private TestExecutors() {

    }

    public static TestExecutor singleThread() {
        return new TestExecutor() {

            public RunnerScheduler testScheduler() {
                return Schedulers.singleThread();
            }

            public RunnerScheduler classesScheduler() {
                return Schedulers.singleThread();
            }
        };
    }

    public static TestExecutor parallelClasses(final ExecutorService executorService) {
        return new TestExecutor() {

            public RunnerScheduler testScheduler() {
                return Schedulers.singleThread();
            }

            public RunnerScheduler classesScheduler() {
                return new ParallelRunnerScheduler(executorService);
            }
        };
    }

    public static TestExecutor parallelTests(final ExecutorService executorService) {
        return new TestExecutor() {

            public RunnerScheduler testScheduler() {
                return new ParallelRunnerScheduler(executorService);
            }

            public RunnerScheduler classesScheduler() {
                return Schedulers.singleThread();
            }
        };
    }
}
