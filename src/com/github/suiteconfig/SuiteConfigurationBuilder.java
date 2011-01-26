package com.github.suiteconfig;

import com.github.suiteconfig.junit.Rules;
import com.github.suiteconfig.junit.filter.Filters;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.rules.MethodRule;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Presents an API for building suite configuration.
 *
 * @author Mairbek Khadikov
 */
public class SuiteConfigurationBuilder implements Supplier<SuiteConfiguration> {
    private final SimpleSuiteConfiguration root;
    private final List<Supplier<SuiteConfiguration>> children = Lists.newLinkedList();

    public SuiteConfigurationBuilder(Supplier<Collection<Class<?>>> classesSupplier) {
        this.root = new SimpleSuiteConfiguration(classesSupplier);
    }

    public SuiteConfigurationBuilder add(SuiteConfiguration configuration) {
        children.add(Suppliers.ofInstance(configuration));
        return this;
    }

    public SuiteConfigurationBuilder add(SuiteConfigurationBuilder configurationBuilder) {
        children.add(configurationBuilder);
        return this;
    }

    public SuiteConfigurationBuilder filter(Filter filter) {
        root.addFilter(filter);
        return this;
    }

    public SuiteConfigurationBuilder applyRule(MethodRule rule) {
        root.addRule(rule);
        return this;
    }


    public SuiteConfiguration get() {
        final List<Class<?>> classes = Lists.newLinkedList();
        final Multimap<Class<?>, Filter> filterMultimap = ArrayListMultimap.create();
        final Multimap<Class<?>, MethodRule> rulesMultimap = ArrayListMultimap.create();

        for (Class<?> clazz : root.classesSupplier().get()) {
            classes.add(clazz);
            filterMultimap.putAll(clazz, root.filters());
            rulesMultimap.putAll(clazz, root.rules());
        }

        for (Supplier<SuiteConfiguration> childSupplier : children) {
            SuiteConfiguration child = childSupplier.get();
            for (Class<?> clazz : child.classesSupplier().get()) {
                classes.add(clazz);

                filterMultimap.putAll(clazz, root.filters());
                filterMultimap.putAll(clazz, child.filters());

                rulesMultimap.putAll(clazz, root.rules());
                rulesMultimap.putAll(clazz, child.rules());
            }
        }

        return new SuiteConfiguration() {
            public Supplier<Collection<Class<?>>> classesSupplier() {
                return Suppliers.<Collection<Class<?>>>ofInstance(classes);
            }

            public Collection<Filter> filters() {
                Filter filter = new Filter() {
                    @Override
                    public boolean shouldRun(Description description) {
                        Class<?> testClass = description.getTestClass();
                        Collection<Filter> filters = filterMultimap.get(testClass);
                        Filter result = Filters.alwaysRun();
                        if (!filters.isEmpty()) {
                            result = Filters.composite(filters);
                        }
                        return result.shouldRun(description);
                    }

                    @Override
                    public String describe() {
                        return "Suite configuration builder filter";
                    }
                };
                return Collections.singleton(filter);
            }

            public List<MethodRule> rules() {
                MethodRule rule = new MethodRule() {
                    public Statement apply(Statement base, FrameworkMethod method, Object target) {
                        Class<?> testClass = method.getMethod().getDeclaringClass();
                        List<MethodRule> methodRules = Lists.newLinkedList(rulesMultimap.get(testClass));

                        MethodRule result = Rules.nothing();
                        if (!methodRules.isEmpty()) {
                            result = Rules.composite(methodRules);
                        }

                        return result.apply(base, method, target);
                    }
                };

                return Collections.singletonList(rule);
            }
        };
    }
}
