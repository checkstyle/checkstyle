package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;

public class UnusedImportsCheckTest extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:45: Unused import - com.puppycrawl.tools.checkstyle.imports.GlobalProperties.",
            "11:8: Unused import - java.lang.String.",
            "13:8: Unused import - java.util.List.",
            "14:8: Unused import - java.util.List.",
            "17:8: Unused import - java.util.Enumeration.",
            "20:8: Unused import - javax.swing.JToggleButton.",
            "22:8: Unused import - javax.swing.BorderFactory.",
            "27:15: Unused import - java.io.File.createTempFile.",
            //"29:8: Unused import - java.awt.Component.", // Should be detected
            "30:8: Unused import - java.awt.Label.",};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImport.java"), expected);
    }

    public void testAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "package-info.java"), expected);
    }

    public void testBug() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportBug.java"), expected);
    }
}
