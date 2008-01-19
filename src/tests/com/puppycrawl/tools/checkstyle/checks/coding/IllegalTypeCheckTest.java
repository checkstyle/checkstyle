package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class IllegalTypeCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp() {
        mCheckConfig = createCheckConfig(IllegalTypeCheck.class);
    }

    @Test
    public void testDefaults() throws Exception {
        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception {
        mCheckConfig.addAttribute("ignoredMethodNames", "table2");

        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        mCheckConfig.addAttribute("format", "^$");

        String[] expected = {
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception {
        mCheckConfig.addAttribute("legalAbstractClassNames", "AbstractClass");

        String[] expected = {
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }
}
