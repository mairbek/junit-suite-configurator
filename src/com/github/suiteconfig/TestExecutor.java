package com.github.suiteconfig;

import org.junit.runners.model.RunnerScheduler;

public interface TestExecutor {
    RunnerScheduler testScheduler();

    RunnerScheduler classesScheduler();

}
