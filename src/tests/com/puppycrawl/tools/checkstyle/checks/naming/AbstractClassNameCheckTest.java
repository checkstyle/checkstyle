package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class AbstractClassNameCheckTest extends BaseCheckTestCase
{
    public void testIllegalAbstractClassName() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbstractClassNameCheck.class);
        final String[] expected = {
            "3:1: Name 'InputAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "6:1: Name 'NonAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "9:1: Name 'FactoryWithBadName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "13:5: Name 'NonAbstractInnerClass' must match pattern '^Abstract.*$|^.*Factory$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }
}
