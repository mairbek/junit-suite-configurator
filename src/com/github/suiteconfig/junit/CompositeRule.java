package com.github.suiteconfig.junit;

import com.google.common.collect.ImmutableList;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.List;

public class CompositeRule implements MethodRule {
    private final List<MethodRule> rules;

    public CompositeRule(List<MethodRule> rules) {
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
