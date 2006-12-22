package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.filters.SuppressElement;
import junit.framework.TestCase;
import java.util.regex.PatternSyntaxException;

/**
 * Tests SuppressionsLoader.
 * @author Rick Giles
 */
public class SuppressionsLoaderTest extends TestCase
{
    public void testNoSuppressions()
        throws CheckstyleException
    {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                System.getProperty("testinputs.dir")
                + "/suppressions_none.xml");
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    public void testMultipleSuppression()
        throws CheckstyleException, PatternSyntaxException
    {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                System.getProperty("testinputs.dir")
                + "/suppressions_multiple.xml");
        final FilterSet fc2 = new FilterSet();
        SuppressElement se0 = new SuppressElement("file0");
        se0.setChecks("check0");
        fc2.addFilter(se0);
        SuppressElement se1 = new SuppressElement("file1");
        se1.setChecks("check1");
        se1.setLines("1,2-3");
        fc2.addFilter(se1);
        SuppressElement se2 = new SuppressElement("file2");
        se2.setChecks("check2");
        se2.setColumns("1,2-3");
        fc2.addFilter(se2);
        SuppressElement se3 = new SuppressElement("file3");
        se3.setChecks("check3");
        se3.setLines("1,2-3");
        se3.setColumns("1,2-3");
        fc2.addFilter(se3);
        assertEquals(fc, fc2);
    }

    public void testNoFile()
    {
        final String fn = System.getProperty("testinputs.dir")
            + "/suppressions_no_file.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - Attribute \"files\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    public void testNoCheck()
    {
        final String fn = System.getProperty("testinputs.dir")
            + "/suppressions_no_check.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse " + fn + " - Attribute \"checks\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    public void testBadInt()
    {
        final String fn = System.getProperty("testinputs.dir")
            + "/suppressions_bad_int.xml";
        try {
            SuppressionsLoader.loadSuppressions(fn);
        }
        catch (CheckstyleException ex) {
            assertTrue(
                ex.getMessage(),
                ex.getMessage().startsWith("number format exception " + fn + " - "));
        }
    }
}
