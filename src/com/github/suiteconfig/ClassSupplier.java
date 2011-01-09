package com.github.suiteconfig;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * @author Mairbek Khadikov
 */
public class ClassSupplier implements Supplier<Collection<Class<?>>> {
    private final ImmutableSet<Class<?>> classes;

    public ClassSupplier(Iterable<Class<?>> classes) {
        this.classes = ImmutableSet.copyOf(classes);
    }

    public ClassSupplier(Class<?>... classes) {
        this.classes = ImmutableSet.of(classes);
    }

    public Collection<Class<?>> get() {
        return classes;
    }
}
