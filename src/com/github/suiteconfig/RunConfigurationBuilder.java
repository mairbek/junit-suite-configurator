package com.github.suiteconfig;

public class RunConfigurationBuilder {
    private final SuiteConfiguration suiteConfiguration;
    private InvocationEnvironment invocationEnvironment;

    public RunConfigurationBuilder(SuiteConfiguration suiteConfiguration) {
        this.suiteConfiguration = suiteConfiguration;
    }

    public void invokeIn(InvocationEnvironment invocationEnvironment) {
        this.invocationEnvironment = invocationEnvironment;
    }

    public RunConfiguration build() {
        return new RunConfiguration() {
            public SuiteConfiguration suiteConfiguration() {
                return suiteConfiguration;
            }

            public InvocationEnvironment testExecutor() {
                return invocationEnvironment;
            }
        };
    }
}