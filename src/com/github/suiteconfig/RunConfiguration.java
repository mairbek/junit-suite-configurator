package com.github.suiteconfig;

public interface RunConfiguration {
    SuiteConfiguration suiteConfiguration();

    InvocationEnvironment testExecutor();
}
