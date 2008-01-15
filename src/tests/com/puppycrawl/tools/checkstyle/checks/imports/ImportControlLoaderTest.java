package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertNotNull;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import org.junit.Test;

public class ImportControlLoaderTest
{
    @Test
    public void testLoad() throws CheckstyleException
    {
        final PkgControl root =
                ImportControlLoader.load(new File(System
                        .getProperty("testinputs.dir")
                        + "/import-control_complete.xml").toURI());
        assertNotNull(root);
    }
}
