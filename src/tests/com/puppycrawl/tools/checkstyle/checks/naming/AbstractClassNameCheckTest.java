package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class AbstractClassNameCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIllegalAbstractClassName() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("checkName", "true");
        checkConfig.addAttribute("checkModifier", "false");

        final String[] expected = {
            "3:1: Name 'InputAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "6:1: Name 'NonAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "9:1: Name 'FactoryWithBadName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "13:5: Name 'NonAbstractInnerClass' must match pattern '^Abstract.*$|^.*Factory$'.",
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testIllegalClassType() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("checkName", "false");
        checkConfig.addAttribute("checkModifier", "true");

        final String[] expected = {
            "26:1: Class 'AbstractClass' must has abstract modifier.",
            "29:1: Class 'Class1Factory' must has abstract modifier.",
            "33:5: Class 'AbstractInnerClass' must has abstract modifier.",
            "38:5: Class 'WellNamedFactory' must has abstract modifier.",
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("checkName", "true");
        checkConfig.addAttribute("checkModifier", "true");

        final String[] expected = {
            "3:1: Name 'InputAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "6:1: Name 'NonAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "9:1: Name 'FactoryWithBadName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "13:5: Name 'NonAbstractInnerClass' must match pattern '^Abstract.*$|^.*Factory$'.",
            "26:1: Class 'AbstractClass' must has abstract modifier.",
            "29:1: Class 'Class1Factory' must has abstract modifier.",
            "33:5: Class 'AbstractInnerClass' must has abstract modifier.",
            "38:5: Class 'WellNamedFactory' must has abstract modifier.",};

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }
}
