package com.github.suiteconfig;

import com.github.suiteconfig.categories.BrokenTest;
import com.github.suiteconfig.categories.SmokeTest;
import com.github.suiteconfig.junit.filter.Filters;
import org.junit.runner.RunWith;

import static com.github.suiteconfig.SuiteConfigurations.classes;
import static com.github.suiteconfig.junit.Schedulers.singleThread;
import static com.github.suiteconfig.junit.filter.Filters.excludeCategories;
import static com.github.suiteconfig.junit.filter.Filters.includeCategories;

@RunWith(Suite.Configuration.class)
public class Suite {

    public static class Configuration extends AbstractConfiguration {

        public Configuration(Class<?> testClass) {
            super(testClass);
        }

        @Override
        protected void configure() {
            run(
                    classes(Test1.class)
                            .filter(includeCategories(SmokeTest.class))
                            .filter(excludeCategories(BrokenTest.class))
            ).with(singleThread());
        }
    }
}
