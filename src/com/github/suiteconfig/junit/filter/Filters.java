package com.github.suiteconfig.junit.filter;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

/**
 * @author Mairbek Khadikov
 */
public class Filters {
    private final static Filter ALWAYS_RUN = new Filter() {
        @Override
        public boolean shouldRun(Description description) {
            return true;
        }

        @Override
        public String describe() {
            return "Always run filter";
        }
    };

    private Filters() {
    }

    public static Filter alwaysRun() {
        return ALWAYS_RUN;
    }

    public static Filter composite(Filter... filters) {
        return new CompositeFilter(filters);
    }

    public static Filter composite(Iterable<Filter> filters) {
        return new CompositeFilter(filters);
    }
}
