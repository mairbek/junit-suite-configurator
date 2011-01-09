package com.github.suiteconfig;

import org.junit.runner.RunWith;

import static com.github.suiteconfig.SuiteConfigurations.classes;
import static com.github.suiteconfig.junit.Schedulers.singleThread;

@RunWith(Suite.Configuration.class)
public class Suite {

    public static class Configuration extends AbstractConfiguration {

        public Configuration(Class<?> testClass) {
            super(testClass);
        }

        @Override
        public void configure() {
            run(classes(Test1.class)).with(singleThread());
        }
    }
}
