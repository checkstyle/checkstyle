package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Enumeration;
import java.util.Iterator;
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
        p.setProperty(Defn.HEADER_FILE_PROP, CheckerTest.getPath("java.header"));
        p.setProperty(Defn.MAX_FILE_LENGTH_PROP, "a");
        p.setProperty(Defn.MAX_LINE_LENGTH_PROP, "66");
        p.setProperty(Defn.ALLOW_TABS_PROP, "true");
        p.setProperty(Defn.LCURLY_METHOD_PROP, "ignore");
        p.setProperty(Defn.LCURLY_OTHER_PROP, "claiea");
        p.setProperty(Defn.RCURLY_PROP, "ignore");
        p.setProperty(Defn.CATCH_BLOCK_PROP, "ignore");
        p.setProperty(Defn.PAREN_PAD_PROP, "ignore");
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
        assertEquals(66, c.getMaxLineLength());
        assertEquals(true, c.isAllowTabs());
    }

    public void test2() throws Exception
    {
        final Properties p = new Properties();
        p.setProperty(Defn.RCURLY_PROP, "claira");
        p.setProperty(Defn.CATCH_BLOCK_PROP, "is great");
        p.setProperty(Defn.PAREN_PAD_PROP, "at sleeping");
        final Configuration c = new Configuration(p, System.out);
        assertNotNull(c);
    }

    public void testGetProperties() throws Exception
    {
        final Configuration c = new Configuration();
        assertEquals("80",
                     c.getProperties().getProperty(Defn.MAX_LINE_LENGTH_PROP));
        c.setIntProperty(Defn.MAX_LINE_LENGTH_PROP, 666);
        assertEquals("666",
                     c.getProperties().getProperty(Defn.MAX_LINE_LENGTH_PROP));
        c.setPatternProperty(Defn.MEMBER_PATTERN_PROP, "bulldogs");
        assertEquals("bulldogs",
                     c.getProperties().getProperty(Defn.MEMBER_PATTERN_PROP));
    }
}
