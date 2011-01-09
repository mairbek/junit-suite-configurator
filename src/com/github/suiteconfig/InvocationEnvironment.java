package com.github.suiteconfig;

import org.junit.runners.model.RunnerScheduler;

public interface InvocationEnvironment {
    RunnerScheduler testScheduler();

    RunnerScheduler classesScheduler();

}
