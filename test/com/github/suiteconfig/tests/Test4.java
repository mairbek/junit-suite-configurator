package com.github.suiteconfig.tests;

import com.github.suiteconfig.categories.BrokenTest;
import com.github.suiteconfig.categories.SmokeTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static junit.framework.Assert.assertTrue;

@Category(SmokeTest.class)
public class Test4 {

    @Test
    public void first() throws Exception {
        assertTrue(true);
    }

    @Test
    public void second() throws Exception {
        assertTrue(true);
    }

    @Test
    @Category(BrokenTest.class)
    public void third() throws Exception {
        assertTrue(false);
    }
}
