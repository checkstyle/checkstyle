package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UnusedImportsCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:45: Unused import - com.puppycrawl.tools.checkstyle.GlobalProperties.",
            "11:8: Unused import - java.lang.String.",
            "13:8: Unused import - java.util.List.",
            "14:8: Unused import - java.util.List.",
            "17:8: Unused import - java.util.Enumeration.",
            "20:8: Unused import - javax.swing.JToggleButton.",
            "22:8: Unused import - javax.swing.BorderFactory.",
            "27:15: Unused import - java.io.File.createTempFile.",
        };
        verify(checkConfig, getPath("InputImport.java"), expected);
    }
}