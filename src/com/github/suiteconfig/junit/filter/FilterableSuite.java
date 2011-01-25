package com.github.suiteconfig.junit.filter;

import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.Collection;

/**
 * A suite that supports filters.
 *
 * @author Mairbek Khadikov
 */
public class FilterableSuite extends Suite {

    public FilterableSuite(RunnerBuilder builder, Class<?> clazz, Collection<Class<?>> classes, Collection<Filter> filters) throws InitializationError, NoTestsRemainException {
        super(builder, clazz, classes.toArray(new Class<?>[classes.size()]));
        filter(new CompositeFilter(filters));
    }

}
