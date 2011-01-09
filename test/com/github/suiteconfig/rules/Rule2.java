package com.github.suiteconfig.rules;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class Rule2 implements MethodRule {
    public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("Rule2: Before execution. " + method.getName());
                try {
                    base.evaluate();
                } finally {
                    System.out.println("Rule2: After Execution");
                }
            }
        };
    }
}
