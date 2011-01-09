package com.github.suiteconfig.junit;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.MethodRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Allows to run tests with specified list of the rules and testScheduler.
 *
 * @author Mairbek Khadikov
 */
public class TestCaseRunner extends BlockJUnit4ClassRunner {
    private final ImmutableList<MethodRule> rules;

    public TestCaseRunner(Class<?> clazz, List<MethodRule> rules, RunnerScheduler scheduler) throws InitializationError {
        super(clazz);
        this.rules = ImmutableList.copyOf(rules);
        setScheduler(scheduler);
    }

    protected Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);
        statement = withPotentialTimeout(method, test, statement);
        statement = withBefores(method, test, statement);
        statement = withAfters(method, test, statement);
        statement = withRules(method, test, statement);
        return statement;
    }

    protected Statement withRules(FrameworkMethod method, Object target,
                                  Statement statement) {
        Statement result = statement;
        for (MethodRule each : getTestClass().getAnnotatedFieldValues(target,
                Rule.class, MethodRule.class)) {
            result = each.apply(result, method, target);
        }

        for (MethodRule each : rules) {
            result = each.apply(result, method, target);
        }

        return result;
    }

}

