package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import junit.framework.TestCase;

public class ImportControlLoaderTest extends TestCase
{
    public void testLoad() throws CheckstyleException
    {
        final PkgControl root = ImportControlLoader.load(System
                .getProperty("testinputs.dir")
                + "/import-control_complete.xml");
        assertNotNull(root);
    }
}
