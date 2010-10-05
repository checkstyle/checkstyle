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
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "true");

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
        checkConfig.addAttribute("ignoreName", "true");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String[] expected = {
            "26:1: Class 'AbstractClass' must be declared as 'abstract'.",
            "29:1: Class 'Class1Factory' must be declared as 'abstract'.",
            "33:5: Class 'AbstractInnerClass' must be declared as 'abstract'.",
            "38:5: Class 'WellNamedFactory' must be declared as 'abstract'.",
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String[] expected = {
            "3:1: Name 'InputAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "6:1: Name 'NonAbstractClassName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "9:1: Name 'FactoryWithBadName' must match pattern '^Abstract.*$|^.*Factory$'.",
            "13:5: Name 'NonAbstractInnerClass' must match pattern '^Abstract.*$|^.*Factory$'.",
            "26:1: Class 'AbstractClass' must be declared as 'abstract'.",
            "29:1: Class 'Class1Factory' must be declared as 'abstract'.",
            "33:5: Class 'AbstractInnerClass' must be declared as 'abstract'.",
            "38:5: Class 'WellNamedFactory' must be declared as 'abstract'.",
        };

        verify(checkConfig, getPath("naming" + File.separator + "InputAbstractClassName.java"), expected);
    }
}
