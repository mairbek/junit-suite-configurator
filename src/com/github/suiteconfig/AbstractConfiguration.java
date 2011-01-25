package com.github.suiteconfig;

import com.github.suiteconfig.junit.filter.ConfigurableRunnerBuilder;
import com.github.suiteconfig.junit.filter.FilterableSuite;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Collection;

public abstract class AbstractConfiguration extends Runner {
    private final Class<?> testClass;
    private Runner delegate;
    private RunConfigurationBuilder runConfigurationBuilder;


    public AbstractConfiguration(Class<?> testClass) {
        this.testClass = testClass;
    }

    @Override
    public final Description getDescription() {
        return delegate().getDescription();

    }

    @Override
    public final void run(RunNotifier notifier) {
        delegate().run(notifier);
    }

    private Runner delegate() {
        if (delegate == null) {
            configure();
            if (runConfigurationBuilder == null) {
                // todo throw exception
                throw new IllegalStateException("");
            }

            RunConfiguration runConfiguration = runConfigurationBuilder.build();
            RunnerBuilder builder = new ConfigurableRunnerBuilder(runConfiguration.suiteConfiguration().rules(), runConfiguration.testExecutor().testScheduler());
            Collection<Class<?>> classes = Lists.newLinkedList();
            classes.addAll(runConfiguration.suiteConfiguration().classesSupplier().get());
            // [mairbek] to avoid cycling dependency
            classes.remove(testClass);
            try {
                FilterableSuite filterableSuite = new FilterableSuite(builder, testClass, classes, runConfiguration.suiteConfiguration().filters());
                filterableSuite.setScheduler(runConfiguration.testExecutor().classesScheduler());
                delegate = filterableSuite;
            } catch (InitializationError initializationError) {
                throw new IllegalStateException("");
            } catch (NoTestsRemainException e) {
                throw new IllegalStateException("");
            }
        }
        return delegate;
    }

    protected abstract void configure();

    public RunConfigurationBuilder run(Supplier<SuiteConfiguration> suiteConfigurationSupplier) {
        return run(suiteConfigurationSupplier.get());
    }

    public RunConfigurationBuilder run(SuiteConfiguration suiteConfigurationSupplier) {
        runConfigurationBuilder = new RunConfigurationBuilder(suiteConfigurationSupplier);
        return runConfigurationBuilder;
    }
}
