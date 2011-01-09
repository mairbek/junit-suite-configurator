package com.github.suiteconfig.junit.filter;

import com.google.common.collect.ImmutableSet;
import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import java.util.Collection;

/**
 * @author Mairbek Khadikov
 */
public abstract class AbstractCategoriesFilter extends Filter {

    protected Collection<Class<?>> categories(Description description) {
        return ImmutableSet.<Class<?>>builder().add(directCategories(description)).add(directCategories(parentDescription(description))).build();
    }

    private Description parentDescription(Description description) {
        return Description.createSuiteDescription(description.getTestClass());
    }

    private Class<?>[] directCategories(Description description) {
        Category annotation = description.getAnnotation(Category.class);
        if (annotation == null)
            return new Class<?>[0];
        return annotation.value();
    }

}
