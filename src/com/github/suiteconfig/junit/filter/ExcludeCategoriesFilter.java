package com.github.suiteconfig.junit.filter;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.junit.runner.Description;

import java.util.Collection;

/**
 * @author Mairbek Khadikov
 */
public class ExcludeCategoriesFilter extends AbstractCategoriesFilter {
    private final ImmutableSet<Class<?>> excluded;

    public ExcludeCategoriesFilter(Iterable<Class<?>> excluded) {
        Preconditions.checkNotNull(excluded);

        this.excluded = ImmutableSet.copyOf(excluded);
    }

    public ExcludeCategoriesFilter(Class<?>... excluded) {
        Preconditions.checkNotNull(excluded);

        this.excluded = ImmutableSet.copyOf(excluded);
    }

    @Override
    public boolean shouldRun(Description description) {
        if (isExcluded(description))
            return false;

        boolean hasChildren = false;
        for (Description each : description.getChildren()) {
            if (!isExcluded(each)) {
                return true;
            }
            hasChildren = true;
        }
        return !hasChildren;
    }

    private boolean isExcluded(Description description) {
        Collection<Class<?>> categories = categories(description);
        for (Class<?> category : categories) {
            if (isExcluded(category)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public String describe() {
        return "Categories filters. Exclude [" + excluded + "] Categories";
    }

    private boolean isExcluded(Class<?> category) {
        for (Class<?> excludedCategory : excluded) {
            if (excludedCategory.isAssignableFrom(category)) {
                return true;
            }
        }
        return false;
    }


}
