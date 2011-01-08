package com.github.suiteconfig;

import com.google.common.base.Supplier;
import org.junit.rules.MethodRule;
import org.junit.runner.manipulation.Filter;

import java.util.Collection;
import java.util.List;

/**
 * Presents suite configuration.
 *
 * @author Mairbek Khadikov
 */
public interface SuiteConfiguration {

    Supplier<Collection<Class<?>>> classesSupplier();

    Collection<Filter> filters();

    List<MethodRule> rules();

}
