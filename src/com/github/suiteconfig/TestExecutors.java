package com.github.suiteconfig;

import com.github.suiteconfig.junit.ParallelRunnerScheduler;
import com.github.suiteconfig.junit.Schedulers;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestExecutors {
    private static final InvocationEnvironment SINGLE_THREAD = new InvocationEnvironment() {

        public RunnerScheduler testScheduler() {
            return Schedulers.singleThread();
        }

        public RunnerScheduler classesScheduler() {
            return Schedulers.singleThread();
        }
    };

    private TestExecutors() {

    }

    public static InvocationEnvironment singleThread() {
        return SINGLE_THREAD;
    }

    public static InvocationEnvironment parallelClasses(final ExecutorService executorService) {
        return new InvocationEnvironment() {

            public RunnerScheduler testScheduler() {
                return Schedulers.singleThread();
            }

            public RunnerScheduler classesScheduler() {
                return new ParallelRunnerScheduler(executorService);
            }
        };
    }

    public static InvocationEnvironment parallelTests(final ExecutorService executorService) {
        return new InvocationEnvironment() {

            public RunnerScheduler testScheduler() {
                return new ParallelRunnerScheduler(executorService);
            }

            public RunnerScheduler classesScheduler() {
                return Schedulers.singleThread();
            }
        };
    }

    public static InvocationEnvironmentBuilder parallel() {
        return new InvocationEnvironmentBuilder();
    }

    public static class InvocationEnvironmentBuilder implements InvocationEnvironment {
        private ParallelType type = ParallelType.CLASSES;
        private int count = 1;

        public RunnerScheduler testScheduler() {
            return delegate().testScheduler();
        }

        public RunnerScheduler classesScheduler() {
            return delegate().classesScheduler();
        }

        public InvocationEnvironmentBuilder classes() {
            type = ParallelType.CLASSES;
            return this;
        }

        public InvocationEnvironmentBuilder methods() {
            type = ParallelType.METHODS;
            return this;
        }

        public InvocationEnvironmentBuilder threadCount(int count) {
            this.count = count;
            return this;
        }

        private InvocationEnvironment delegate() {
            switch (type) {
                case CLASSES:
                    return parallelClasses(Executors.newFixedThreadPool(count));
                case METHODS:
                    return parallelTests(Executors.newFixedThreadPool(count));
                default:
                    throw new IllegalStateException("");

            }
        }
    }

    public enum ParallelType {
        CLASSES, METHODS
    }

}
