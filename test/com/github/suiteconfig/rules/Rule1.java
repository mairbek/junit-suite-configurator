package com.github.suiteconfig.rules;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class Rule1 implements MethodRule {
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("Rule1: Before execution. " + target.getClass() + method.getName());
                try {
                    base.evaluate();
                } finally {
                    System.out.println("Rule1: After Execution");
                }
            }
        };
    }
}
