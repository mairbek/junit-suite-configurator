package com.github.suiteconfig.junit;

import com.google.common.collect.ImmutableList;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.Collection;

public class CompositeRule implements MethodRule {
    private final Collection<MethodRule> rules;

    public CompositeRule(Collection<MethodRule> rules) {
        this.rules = ImmutableList.copyOf(rules);
    }

    public CompositeRule(MethodRule... rules) {
        this.rules = ImmutableList.copyOf(rules);
    }

    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement statement = base;
        for (MethodRule rule : rules) {
            statement = rule.apply(statement, method, target);
        }
        return statement;
    }
}
