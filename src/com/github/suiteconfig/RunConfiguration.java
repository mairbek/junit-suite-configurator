package com.github.suiteconfig;

import org.junit.runners.model.RunnerScheduler;

public interface RunConfiguration {
    SuiteConfiguration suiteConfiguration();

    RunnerScheduler scheduler();
}
