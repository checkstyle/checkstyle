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
        p.setProperty(Defn.CATCH_BLOCK_PROP, "text");
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
        assertEquals(BlockOption.TEXT, c.getCatchBlock());
    }

    public void test2() throws Exception
    {
        final Properties p = new Properties();
        p.setProperty(Defn.CATCH_BLOCK_PROP, "is great");
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
    }
}
