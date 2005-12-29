package com.puppycrawl.tools.checkstyle.filters;

import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

import junit.framework.TestCase;

/** Tests SuppressElementFilter */
public class SuppressElementTest extends TestCase
{
    private SuppressElement filter;
    
    public void setUp()
        throws PatternSyntaxException
    {
        filter = new SuppressElement("Test");
        filter.setChecks("Test");
    }
    
    public void testDecideDefault()
    {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertTrue(ev.getFileName(), filter.accept(ev));
    }
    
    public void testDecideLocalizedMessage()
    {
        LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null, null, this.getClass());
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file and check names
        assertFalse("Names match", filter.accept(ev));
    }
    
    public void testDecideByLine()
    {
        LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, this.getClass());
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and line
        filter.setLines("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setLines("1-9, 11");
        assertTrue("Not in 1-9, 11", filter.accept(ev));
    }
    
    public void testDecideByColumn()
    {
        LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, this.getClass());
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and column
        filter.setColumns("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setColumns("1-9, 11");
        assertTrue("Not in 1-9, 1)", filter.accept(ev));
    }

    public void testEquals() throws PatternSyntaxException
    {
        final SuppressElement filter2 = new SuppressElement("Test");
        filter2.setChecks("Test");
        assertEquals("filter, filter2", filter, filter2);
        final SuppressElement filter3 = new SuppressElement("Test");
        filter3.setChecks("Test3");
        assertFalse("filter, filter3", filter.equals(filter3));
        filter.setColumns("1-10");
        assertFalse("filter, filter2", filter.equals(filter2));
        filter2.setColumns("1-10");
        assertEquals("filter, filter2", filter, filter2);
        filter.setColumns(null);
        assertFalse("filter, filter2", filter.equals(filter2));
        filter2.setColumns(null);
        filter.setLines("3,4");
        assertFalse("filter, filter2", filter.equals(filter2));
        filter2.setLines("3,4");
        assertEquals("filter, filter2", filter, filter2);
        filter.setColumns("1-10");
        assertFalse("filter, filter2", filter.equals(filter2));
        filter2.setColumns("1-10");
        assertEquals("filter, filter2", filter, filter2);       
    }
}
