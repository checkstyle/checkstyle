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
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_none.xml");
        final FilterSet fc2 = new FilterSet();
        assertEquals(fc, fc2);
    }

    public void testMultipleSuppression()
        throws CheckstyleException, PatternSyntaxException
    {
        final FilterSet fc =
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_multiple.xml");
        final FilterSet fc2 = new FilterSet();
        SuppressElement se0 = new SuppressElement("file0", "check0");
        fc2.addFilter(se0);
        SuppressElement se1 = new SuppressElement("file1", "check1");
        se1.setLines("1,2-3");
        fc2.addFilter(se1);
        SuppressElement se2 = new SuppressElement("file2", "check2");
        se2.setColumns("1,2-3");
        fc2.addFilter(se2);
        SuppressElement se3 = new SuppressElement("file3", "check3");
        se3.setLines("1,2-3");
        se3.setColumns("1,2-3");
        fc2.addFilter(se3);
        assertEquals(fc, fc2);
    }

    public void testNoFile()
        throws CheckstyleException
    {
        try {
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_no_file.xml");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_no_file.xml - Attribute \"files\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    public void testNoCheck()
        throws CheckstyleException
    {
        try {
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_no_check.xml");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "unable to parse src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_no_check.xml - Attribute \"checks\" is required and must be specified for element type \"suppress\".",
                ex.getMessage());
        }
    }

    public void testBadInt()
        throws CheckstyleException
    {
        try {
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_bad_int.xml");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                "number format exception src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_bad_int.xml - For input string: \"a\"",
                ex.getMessage());
        }
    }
}
