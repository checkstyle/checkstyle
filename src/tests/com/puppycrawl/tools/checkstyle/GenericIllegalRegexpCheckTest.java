package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck;

public class GenericIllegalRegexpCheckTest
        extends BaseCheckTestCase
{
    public GenericIllegalRegexpCheckTest(String aName)
    {
        super(aName);
    }

    public void testIt()
            throws Exception
    {
        String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(GenericIllegalRegexpCheck.class.getName());
        checkConfig.addProperty("format", illegal);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'."
        };
        verify(c, fname, expected);
    }
}
