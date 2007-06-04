package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.File;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import junit.framework.TestCase;

public class ImportControlLoaderTest extends TestCase
{
    public void testLoad() throws CheckstyleException
    {
        final PkgControl root =
                ImportControlLoader.load(new File(System
                        .getProperty("testinputs.dir")
                        + "/import-control_complete.xml").toURI());
        assertNotNull(root);
    }
}
