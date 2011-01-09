package com.github.suiteconfig;

import org.junit.runners.model.RunnerScheduler;

public class RunConfigurationBuilder {
    private final SuiteConfiguration suiteConfiguration;
    private RunnerScheduler scheduler;

    public RunConfigurationBuilder(SuiteConfiguration suiteConfiguration) {
        this.suiteConfiguration = suiteConfiguration;
    }

    public RunConfigurationBuilder with(RunnerScheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public RunConfiguration build() {
        return new RunConfiguration() {
            public SuiteConfiguration suiteConfiguration() {
                return suiteConfiguration;
            }

            public RunnerScheduler scheduler() {
                return scheduler;
            }
        };
    }
}
