package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.HeaderCheck;
import com.puppycrawl.tools.checkstyle.checks.RegexpHeaderCheck;

public class HeaderCheckTest extends BaseCheckTestCase
{
    public HeaderCheckTest(String aName)
    {
        super(aName);
    }

    public void testStaticHeader()
            throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(HeaderCheck.class.getName());
        checkConfig.addProperty("headerFile", getPath("java.header"));
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("inputHeader.java");
        final String[] expected = {
            "1:1: Missing a header - not enough lines in file."
        };
        verify(c, fname, expected);
    }

    public void testRegexpHeader()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(RegexpHeaderCheck.class.getName());
        checkConfig.addProperty("headerFile", getPath("regexp.header"));
        checkConfig.addProperty("ignoreLines", "4,5");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputScopeAnonInner.java");
        final String[] expected = {
            "3:1: Line does not match expected header line of '// Created: 2002'."
        };
        verify(c, fname, expected);
    }


}
