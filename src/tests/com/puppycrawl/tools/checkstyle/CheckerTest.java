package com.puppycrawl.tools.checkstyle;

import java.io.File;

import junit.framework.TestCase;

public class CheckerTest extends TestCase
{
    public void testDosBasedir() throws Exception
    {
        Checker c = new Checker();

        c.setBasedir("c:/a\\b/./c\\..\\d");
        assertEquals("C:\\a\\b\\d", c.getBasedir());
    }

    public void testOsBasedir() throws Exception
    {
        Checker c = new Checker();

        // we need something to create absolute path
        // let's take testinputs.dir
        String testinputs_dir = System.getProperty("testinputs.dir");

        if (!testinputs_dir.endsWith(File.separator)) {
            testinputs_dir += File.separator;
        }

        c.setBasedir(testinputs_dir + "indentation/./..\\coding\\");


        assertEquals(testinputs_dir + "coding", c.getBasedir());
    }
}
