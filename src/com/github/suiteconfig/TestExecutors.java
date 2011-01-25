package com.github.suiteconfig;

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
                return Schedulers.asynchronous(executorService);
            }
        };
    }

    public static InvocationEnvironment parallelMethods(final ExecutorService executorService) {
        return new InvocationEnvironment() {

            public RunnerScheduler testScheduler() {
                return Schedulers.asynchronous(executorService);
            }

            public RunnerScheduler classesScheduler() {
                return Schedulers.singleThread();
            }
        };
    }

    public static InvocationEnvironment parallelTests(ExecutorService executorService) {
        return new ParallelTestsInvocationEnvironment(executorService);
    }


    public static InvocationEnvironmentBuilder parallel() {
        return new InvocationEnvironmentBuilder();
    }

    public static class InvocationEnvironmentBuilder implements InvocationEnvironment {
        private ParallelType type = ParallelType.CLASSES;
        private int count = 1;
        private InvocationEnvironment delegate;

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

        public InvocationEnvironmentBuilder tests() {
            type = ParallelType.TESTS;
            return this;
        }

        public InvocationEnvironmentBuilder threadCount(int count) {
            this.count = count;
            return this;
        }

        private InvocationEnvironment delegate() {
            if (delegate == null) {
                switch (type) {
                    case CLASSES:
                        delegate = parallelClasses(Executors.newFixedThreadPool(count));
                        break;
                    case METHODS:
                        delegate = parallelMethods(Executors.newFixedThreadPool(count));
                        break;
                    case TESTS:
                        delegate = parallelTests(Executors.newFixedThreadPool(count));
                        break;
                    default:
                        throw new IllegalStateException("");
                }
            }
            return delegate;
        }
    }

    private enum ParallelType {
        CLASSES, METHODS, TESTS
    }

}
