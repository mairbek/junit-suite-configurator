package com.github.suiteconfig.junit;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.Collection;

public class Rules {
    private static MethodRule INSTANCE = new MethodRule() {
        public Statement apply(Statement base, FrameworkMethod method, Object target) {
            return base;
        }
    };

    private Rules() {

    }

    public static CompositeRule composite(MethodRule... rules) {
        return new CompositeRule(rules);
    }

    public static CompositeRule composite(Collection<MethodRule> rules) {
        return new CompositeRule(rules);
    }

    public static MethodRule nothing() {
        return INSTANCE;
    }
}
