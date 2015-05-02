////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.LogMode;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class MainTest
{
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final StandardErrorStreamLog errorLog = new StandardErrorStreamLog(LogMode.LOG_ONLY);
    @Rule
    public final StandardOutputStreamLog standardLog = new StandardOutputStreamLog(LogMode.LOG_ONLY);

    @Test
    public void testVersionPrint()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Checkstyle version: null\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-v");
    }

    @Test
    public void testWrongArgument()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                String usage = "Unrecognized option: -w\n"
                    + "usage: java com.puppycrawl.tools.checkstyle.Main [options] -c <config.xml>\n"
                    + "            file...\n"
                    + " -c <arg>   Sets the check configuration file to use.\n"
                    + " -f <arg>   Sets the output format. (plain|xml). Defaults to plain\n"
                    + " -o <arg>   Sets the output file. Defaults to stdout\n"
                    + " -p <arg>   Loads the properties file\n"
                    + " -v         Print product version and exit\n";

                assertEquals(usage, standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-w");
    }

    @Test
    public void testNoConfigSpecified()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Must specify a config XML file.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testNonExistingTargetFile()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Must specify files to process, found 0.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "NonexistingFile.java");
    }

    @Test
    public void testNonExistingConfigFile()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Checkstyle ends with 1 errors.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/main/resources/non_existing_config.xml"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testNonExistingOutputFormat()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Invalid output format."
                        + " Found '" + "xmlp" + "' but expected 'plain' or 'xml'.\n"
                        , standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "-f" , "xmlp"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFile()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Starting audit...\n"
                        + "Audit done.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileXmlOutput()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion() throws IOException
            {
                String currentPath = new File(".").getCanonicalPath();
                final ResourceBundle compilationProperties =
                        ResourceBundle.getBundle("checkstylecompilation");
                String version = compilationProperties.getString("checkstyle.compile.version");
                assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<checkstyle version=\"" + version + "\">\n"
                        + "<file name=\""
                        + currentPath
                        + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java\">\n"
                        + "</file>\n"
                        + "</checkstyle>\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml"
                , "-f", "xml"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutput()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Starting audit...\n"
                        + "Audit done.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml"
                , "-f", "plain"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileWithViolations()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion() throws IOException
            {
                String currentPath = new File(".").getCanonicalPath();
                assertEquals("Starting audit...\n"
                    + currentPath
                    + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java:3:14: "
                    + "warning: Name 'InputMain' must match pattern '^[a-z0-9]*$'.\n"
                    + currentPath
                    + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java:5:7: "
                    + "warning: Name 'InputMainInner' must match pattern '^[a-z0-9]*$'.\n"
                    + "Audit done.\n"
                    , standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname2.xml"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileWithError()
            throws Exception
    {
        exit.expectSystemExitWithStatus(2);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion() throws IOException
            {
                String currentPath = new File(".").getCanonicalPath();
                assertEquals("Starting audit...\n"
                        + currentPath
                        + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java:3:14: "
                        + "Name 'InputMain' must match pattern '^[a-z0-9]*$'.\n"
                        + currentPath
                        + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java:5:7: "
                        + "Name 'InputMainInner' must match pattern '^[a-z0-9]*$'.\n"
                        + "Audit done.\n"
                        + "Checkstyle ends with 2 errors.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname2-error.xml"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToNonExistingFile()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Could not find file 'myjava.java'.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml"
                , "-f", "plain"
                , "-o", "myjava.java"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile()
            throws Exception
    {
        final File file = temporaryFolder.newFile("file.output");
        //Assert.assertTrue(file.getTotalSpace() == 0);

        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                //Assert.assertTrue(file.getTotalSpace() > 0);
                assertEquals("", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml"
                , "-f", "plain"
                , "-o", file.getCanonicalPath()
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties()
            throws Exception
    {
        exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Starting audit...\n"
                        + "Audit done.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "config-classname-prop.xml"
                , "-p", "src/test/resources/com/puppycrawl/tools/checkstyle/mycheckstyle.properties"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistingProperties()
            throws Exception
    {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion()
        {
            public void checkAssertion()
            {
                assertEquals("Could not find file 'nonexisting.properties'.\n", standardLog.getLog());
                assertEquals("", errorLog.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "config-classname-prop.xml"
                , "-p", "nonexisting.properties"
                , "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }
}
