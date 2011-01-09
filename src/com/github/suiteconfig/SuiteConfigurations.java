package com.github.suiteconfig;

public class SuiteConfigurations {
    private SuiteConfigurations() {

    }

    public static SuiteConfigurationBuilder classes(Class<?> classes) {
        return new SuiteConfigurationBuilder(new ClassSupplier(classes));
    }
}
