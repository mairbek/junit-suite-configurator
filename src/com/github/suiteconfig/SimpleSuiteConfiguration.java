package com.github.suiteconfig;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
    private final Collection<Filter> filters = Sets.newHashSet();
    private final List<MethodRule> rules = Lists.newLinkedList();

    public SimpleSuiteConfiguration(Supplier<Collection<Class<?>>> classesSupplier) {
        this.classesSupplier = classesSupplier;
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

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void addRule(MethodRule rule) {
        rules.add(rule);
    }
}
