package com.github.suiteconfig.junit.filter;

import com.github.suiteconfig.junit.TestCaseRunner;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.internal.builders.NullBuilder;
import org.junit.rules.MethodRule;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

import java.lang.reflect.Method;
import java.util.List;

/**
 * {@link org.junit.runners.model.RunnerBuilder} implementation that allows to specify rules and runner testScheduler.
 *
 * @author Mairbek Khadikov
 */
public class ConfigurableRunnerBuilder extends RunnerBuilder {
    private final List<MethodRule> rules;
    private final RunnerScheduler runnerScheduler;

    public ConfigurableRunnerBuilder(List<MethodRule> rules, RunnerScheduler runnerScheduler) {
        this.rules = ImmutableList.copyOf(rules);
        this.runnerScheduler = runnerScheduler;
    }

    @Override
    public Runner runnerForClass(Class<?> testClass) throws Throwable {
        RunnerBuilder delegate = new NullBuilder();

        if (isTestClass(testClass)) {
            delegate = new RunnerBuilder() {
                @Override
                public Runner runnerForClass(Class<?> testClass) throws Throwable {
                    return new TestCaseRunner(testClass, rules, runnerScheduler);
                }
            };
        }
        return delegate.runnerForClass(testClass);
    }

    private static boolean isTestClass(Class<?> testClass) {
        Method[] methods = testClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                return true;
            }
        }
        return false;
    }

}
