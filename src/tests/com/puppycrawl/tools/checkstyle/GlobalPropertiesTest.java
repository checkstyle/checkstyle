package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Properties;

public class GlobalPropertiesTest
    extends TestCase
{
    public GlobalPropertiesTest(String name)
    {
        super(name);
    }

    public void test1() throws Exception
    {
        final Properties p = new Properties();
        final GlobalProperties c = new GlobalProperties(p, System.out);
        assertNotNull(c);
    }

    public void test2() throws Exception
    {
        final Properties p = new Properties();
        final GlobalProperties c = new GlobalProperties(p, System.out);
        assertNotNull(c);
    }
}
