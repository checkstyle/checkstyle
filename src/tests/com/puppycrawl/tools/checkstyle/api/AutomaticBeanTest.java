package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class AutomaticBeanTest
{
    private static class TestBean extends AutomaticBean
    {
        public void setName(String aName)
        {
        }
    }

    private final DefaultConfiguration mConf = new DefaultConfiguration(
            "testConf");

    TestBean mTestBean = new TestBean();

    @Test(expected = CheckstyleException.class)
    public void testNoSuchAttribute() throws CheckstyleException
    {
        mConf.addAttribute("NonExisting", "doesn't matter");
        mTestBean.configure(mConf);
    }
}
