package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import junit.framework.TestCase;

public class AutomaticBeanTest extends TestCase
{
    private class TestBean extends AutomaticBean
    {
        private String mName = null;

        public void setName(String aName)
        {
            mName = aName;
        }
    }

    DefaultConfiguration mConf = null;

    TestBean mTestBean = new TestBean();

    public void setUp()
    {
        mConf = new DefaultConfiguration("testConf");
    }

    public void testNoSuchAttribute()
    {
        mConf.addAttribute("NonExisting", "doesn't matter");
        try {
            mTestBean.configure(mConf);
            fail("AutomaticBean.configure() accepted nonexisting attribute name");
        }
        catch (CheckstyleException ex)
        {
            // expected exception
        }
        assertEquals(mTestBean.mName, null);

    }
}
