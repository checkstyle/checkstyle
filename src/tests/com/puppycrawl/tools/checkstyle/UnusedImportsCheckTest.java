package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.UnusedImportsCheck;

public class UnusedImportsCheckTest
    extends BaseCheckTestCase
{
    public UnusedImportsCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(UnusedImportsCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "8:45: Unused import - com.puppycrawl.tools.checkstyle.GlobalProperties.",
            "11:8: Unused import - java.lang.String.",
            "13:8: Unused import - java.util.List.",
            "14:8: Unused import - java.util.List.",
            "17:8: Unused import - java.util.Enumeration.",
            "20:8: Unused import - javax.swing.JToggleButton.",
            "22:8: Unused import - javax.swing.BorderFactory.",
        };
        verify(c, fname, expected);
    }
}
