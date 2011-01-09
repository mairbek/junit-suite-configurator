package com.github.suiteconfig.junit.filter;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.junit.runner.Description;

import java.util.Collection;

/**
 * @author Mairbek Khadikov
 */
public class IncludeCategoriesFilter extends AbstractCategoriesFilter {
    private final ImmutableSet<Class<?>> included;

    public IncludeCategoriesFilter(Iterable<Class<?>> included) {
        Preconditions.checkNotNull(included);

        this.included = ImmutableSet.copyOf(included);
    }

    public IncludeCategoriesFilter(Class<?>... included) {
        Preconditions.checkNotNull(included);

        this.included = ImmutableSet.copyOf(included);
    }

    @Override
    public boolean shouldRun(Description description) {
        if (isIncluded(description))
            return true;
        for (Description each : description.getChildren())
            if (shouldRun(each))
                return true;
        return false;
    }

    private boolean isIncluded(Description description) {
        Collection<Class<?>> categories = categories(description);
        for (Class<?> category : categories) {
            if (isIncluded(category)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public String describe() {
        return "Categories filters. Include [" + included + "] Categories";
    }

    private boolean isIncluded(Class<?> category) {
        for (Class<?> includedCategory : included) {
            if (includedCategory.isAssignableFrom(category)) {
                return true;
            }
        }
        return false;
    }

}
