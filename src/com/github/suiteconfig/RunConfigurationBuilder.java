package com.github.suiteconfig;

public class RunConfigurationBuilder {
    private final SuiteConfiguration suiteConfiguration;
    private TestExecutor testExecutor;

    public RunConfigurationBuilder(SuiteConfiguration suiteConfiguration) {
        this.suiteConfiguration = suiteConfiguration;
    }

    public void with(TestExecutor testExecutor) {
        this.testExecutor = testExecutor;
    }

    public RunConfiguration build() {
        return new RunConfiguration() {
            public SuiteConfiguration suiteConfiguration() {
                return suiteConfiguration;
            }

            public TestExecutor testExecutor() {
                return testExecutor;
            }
        };
    }
}