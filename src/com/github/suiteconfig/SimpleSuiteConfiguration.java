package com.github.suiteconfig;

import com.google.common.base.Supplier;
import org.junit.rules.MethodRule;
import org.junit.runner.manipulation.Filter;

import java.util.Collection;
import java.util.List;

/**
 * Simple implementation of {@link SuiteConfiguration}
 *
 * @author Mairbek Khadikov
 */
public class SimpleSuiteConfiguration implements SuiteConfiguration {
    private final Supplier<Collection<Class<?>>> classesSupplier;
    private final Collection<Filter> filters;
    private final List<MethodRule> rules;

    public SimpleSuiteConfiguration(Supplier<Collection<Class<?>>> classesSupplier, Collection<Filter> filters, List<MethodRule> rules) {
        this.classesSupplier = classesSupplier;
        this.filters = filters;
        this.rules = rules;
    }

    public Supplier<Collection<Class<?>>> classesSupplier() {
        return classesSupplier;
    }

    public Collection<Filter> filters() {
        return filters;
    }

    public List<MethodRule> rules() {
        return rules;
    }
}
