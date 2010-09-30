package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class UnusedImportsCheckTest extends BaseCheckTestSupport
{
    @Test
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

    @Test
    public void testAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "package-info.java"), expected);
    }

    @Test
    public void testBug() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportBug.java"), expected);
    }
}
