package com.puppycrawl.tools.checkstyle;

import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FilterChain;
import com.puppycrawl.tools.checkstyle.filters.SuppressElement;

/**
 * Tests SuppressionsLoader.
 * @author Rick Giles
 */
public class SuppressionsLoaderTest extends TestCase
{
    public void testNoSuppressions()
        throws CheckstyleException
    {
        final FilterChain fc =
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_none.xml");
        final FilterChain fc2 = new FilterChain();
        assertEquals(fc, fc2);
    }
    
    public void testMultipleSuppression()
        throws CheckstyleException, RESyntaxException
    {
        final FilterChain fc =
            SuppressionsLoader.loadSuppressions(
                "src/testinputs/com/puppycrawl/tools/checkstyle/suppressions_multiple.xml");
        final FilterChain fc2 = new FilterChain();
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
