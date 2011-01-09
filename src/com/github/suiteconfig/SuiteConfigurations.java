package com.github.suiteconfig;

public class SuiteConfigurations {
    private SuiteConfigurations() {

    }

    public static SuiteConfigurationBuilder classes(Class<?>... classes) {
        return new SuiteConfigurationBuilder(new ClassSupplier(classes));
    }

    public static SuiteConfigurationBuilder allInPackage(String packageName, boolean recursive) {
        return new SuiteConfigurationBuilder(new PackageSupplier(packageName, recursive));
    }

    public static SuiteConfigurationBuilder allInPackage(String packageName) {
        // todo add * matcher here
        return allInPackage(packageName, true);
    }
}
