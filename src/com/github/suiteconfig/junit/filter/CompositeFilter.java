package com.github.suiteconfig.junit.filter;

import com.google.common.collect.ImmutableSet;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

/**
 * Groups JUnit filters.
 *
 * @author Mairbek Khadikov
 */
public class CompositeFilter extends Filter {
    private final ImmutableSet<Filter> filters;

    public CompositeFilter(Iterable<Filter> filters) {
        this.filters = ImmutableSet.copyOf(filters);
    }

    public CompositeFilter(Filter... filters) {
        this.filters = ImmutableSet.copyOf(filters);
    }


    @Override
    public boolean shouldRun(Description description) {
        for (Filter filter : filters) {
            if (!filter.shouldRun(description)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String describe() {
        return "CompositeFilter. Filters: " + filters;
    }

}
