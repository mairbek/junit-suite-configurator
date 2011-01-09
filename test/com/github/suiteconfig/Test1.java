package com.github.suiteconfig;

import com.github.suiteconfig.categories.BrokenTest;
import com.github.suiteconfig.categories.SmokeTest;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class Test1 {

    @Test
    @Category(SmokeTest.class)
    public void test() throws Exception {
        Assert.assertTrue(true);
    }

    @Test
    @Category({SmokeTest.class, BrokenTest.class})
    public void fail() throws Exception {
        Assert.assertTrue(false);
    }

    @Test
    public void noCategory() throws Exception {
        Assert.assertTrue(false);
    }
}
