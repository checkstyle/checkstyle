////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import java.util.regex.PatternSyntaxException;
import org.junit.Before;
import org.junit.Test;

/** Tests SuppressElementFilter */
public class SuppressElementTest
{
    private SuppressElement filter;

    @Before
    public void setUp()
        throws PatternSyntaxException
    {
        filter = new SuppressElement("Test");
        filter.setChecks("Test");
    }

    @Test
    public void testDecideDefault()
    {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertTrue(ev.getFileName(), filter.accept(ev));
    }

    @Test
    public void testDecideLocalizedMessage()
    {
        LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null, null, this.getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file and check names
        assertFalse("Names match", filter.accept(ev));
    }

    @Test
    public void testDecideByLine()
    {
        LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, this.getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and line
        filter.setLines("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setLines("1-9, 11");
        assertTrue("Not in 1-9, 11", filter.accept(ev));
    }

    @Test
    public void testDecideByColumn()
    {
        LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, this.getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and column
        filter.setColumns("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setColumns("1-9, 11");
        assertTrue("Not in 1-9, 1)", filter.accept(ev));
    }

    @Test
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
