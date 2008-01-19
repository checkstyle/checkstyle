package com.puppycrawl.tools.checkstyle.checks.naming;

import static org.junit.Assert.fail;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import org.junit.Test;

public class ConstantNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIllegalRegexp()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        checkConfig.addAttribute("format", "\\");
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
            "25:29: Name 'badConstant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "142:30: Name 'BAD__NAME' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'."
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInterfaceAndAnnotation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
            "24:16: Name 'data' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "64:16: Name 'data' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'."
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testDefault1()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputConstantNames.java"), expected);
    }
}
