package com.github.suiteconfig.junit.filter;

import com.google.common.base.Preconditions;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

/**
 * @author Mairbek Khadikov
 */
public class ExcludeClassFilter extends Filter {
    private final Class<?> clazz;

    public ExcludeClassFilter(Class<?> clazz) {
        Preconditions.checkNotNull(clazz);

        this.clazz = clazz;
    }

    @Override
    public boolean shouldRun(Description description) {
        return !clazz.equals(description.getTestClass());
    }

    @Override
    public String describe() {
        return "Class Filter. Class: " + clazz;
    }
}
