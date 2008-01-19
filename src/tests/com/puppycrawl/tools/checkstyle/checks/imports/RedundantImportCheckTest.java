package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class RedundantImportCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantImportCheck.class);
        final String[] expected = {
            "7:1: Redundant import from the same package - com.puppycrawl.tools.checkstyle.imports.*.",
            "8:38: Redundant import from the same package - com.puppycrawl.tools.checkstyle.imports.GlobalProperties.",
            "10:1: Redundant import from the java.lang package - java.lang.*.",
            "11:1: Redundant import from the java.lang package - java.lang.String.",
            "14:1: Duplicate import to line 13 - java.util.List.",
            "26:1: Duplicate import to line 25 - javax.swing.WindowConstants.*."
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }
}
