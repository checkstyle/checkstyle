package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;
import com.puppycrawl.tools.checkstyle.checks.ParameterFormatCheck;
import com.puppycrawl.tools.checkstyle.checks.TypeNameCheck;
import com.puppycrawl.tools.checkstyle.checks.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.PackageNameCheck;

public class PackageNameCheckTest
    extends BaseCheckTestCase
{
    public PackageNameCheckTest(String aName)
    {
        super(aName);
    }

    public void testSpecified()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(PackageNameCheck.class.getName());
        checkConfig.addProperty("format", "[A-Z]+");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "6:9: Name 'com.puppycrawl.tools.checkstyle' must match pattern '[A-Z]+'.",
        };
        verify(c, fname, expected);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(PackageNameCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
