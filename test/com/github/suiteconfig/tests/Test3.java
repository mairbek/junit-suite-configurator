package com.github.suiteconfig.tests;

import com.github.suiteconfig.categories.SmokeTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static junit.framework.Assert.assertTrue;

@Category(SmokeTest.class)
public class Test3 {

    @Test
    public void first() throws Exception {
        assertTrue(true);
    }

    @Test
    public void second() throws Exception {
        assertTrue(true);
    }

    @Test
    public void third() throws Exception {
        assertTrue(true);
    }
}
