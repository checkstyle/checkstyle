package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Properties;

public class ConfigurationTest
    extends TestCase
{
    public ConfigurationTest(String name)
    {
        super(name);
    }

    public void test1() throws Exception
    {
        final Properties p = new Properties();
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
    }

    public void test2() throws Exception
    {
        final Properties p = new Properties();
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
    }
}
