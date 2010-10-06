////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import java.io.File;
import java.util.TreeSet;
import org.junit.Test;

public class CheckerTest
{
    @Test
    public void testDosBasedir() throws Exception
    {
        final Checker c = new Checker();

        c.setBasedir("c:/a\\b/./c\\..\\d");
        assertEquals("C:\\a\\b\\d", c.getBasedir());
    }

    @Test
    public void testOsBasedir() throws Exception
    {
        final Checker c = new Checker();

        // we need something to create absolute path
        // let's take testinputs.dir
        String testinputs_dir = System.getProperty("testinputs.dir")
                .replace('/', File.separatorChar)
                .replace('\\', File.separatorChar);

        if (!testinputs_dir.endsWith(File.separator)) {
            testinputs_dir += File.separator;
        }

        final String instr = testinputs_dir + "indentation/./..\\coding\\";
        c.setBasedir(instr);
        assertTrue((testinputs_dir + "coding").equalsIgnoreCase(c.getBasedir()));
    }

    @Test
    public void testDestroy() throws Exception
    {
        final DebugChecker c = new DebugChecker();
        final DebugAuditAdapter aa = new DebugAuditAdapter();
        c.addListener(aa);
        final DebugFilter f = new DebugFilter();
        c.addFilter(f);

        c.destroy(); // should remove al listeners and filters

        // Let's try fire some events
        c.fireAuditStarted();
        c.fireAuditFinished();
        c.fireFileStarted("Some File Name");
        c.fireFileFinished("Some File Name");

        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);

        assertFalse("Checker.destroy() doesn't remove listeners.", aa.wasCalled());
        assertFalse("Checker.destroy() doesn't remove filters.", f.wasCalled());
    }

    @Test
    public void testAddListener() throws Exception
    {
        final DebugChecker c = new DebugChecker();
        final DebugAuditAdapter aa = new DebugAuditAdapter();
        c.addListener(aa);

        // Let's try fire some events
        c.fireAuditStarted();
        assertTrue("Checker.fireAuditStarted() doesn't call listener", aa.wasCalled());

        aa.resetListener();
        c.fireAuditFinished();
        assertTrue("Checker.fireAuditFinished() doesn't call listener", aa.wasCalled());

        aa.resetListener();
        c.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", aa.wasCalled());

        aa.resetListener();
        c.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", aa.wasCalled());

        aa.resetListener();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call listener", aa.wasCalled());
    }

    @Test
    public void testRemoveListener() throws Exception
    {
        final DebugChecker c = new DebugChecker();
        final DebugAuditAdapter aa = new DebugAuditAdapter();
        final DebugAuditAdapter aa2 = new DebugAuditAdapter();
        c.addListener(aa);
        c.addListener(aa2);
        c.removeListener(aa);

        // Let's try fire some events
        c.fireAuditStarted();
        assertTrue("Checker.fireAuditStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditStarted() does call removed listener", aa.wasCalled());

        aa2.resetListener();
        c.fireAuditFinished();
        assertTrue("Checker.fireAuditFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditFinished() does call removed listener", aa.wasCalled());

        aa2.resetListener();
        c.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileStarted() does call removed listener", aa.wasCalled());

        aa2.resetListener();
        c.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileFinished() does call removed listener", aa.wasCalled());

        aa2.resetListener();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed listener", aa.wasCalled());

    }

    @Test
    public void testAddFilter() throws Exception
    {
        final DebugChecker c = new DebugChecker();
        final DebugFilter f = new DebugFilter();

        c.addFilter(f);

        // Let's try fire some events
        // we do not call filter in fireAuditStarted() (fix for 1080343)
//         c.fireAuditStarted();
//         assertTrue("Checker.fireAuditStarted() doesn't call filter", f.wasCalled());

        // we do not call filter in fireAuditFinished() (fix for 1080343)
//         f.resetFilter();
//         c.fireAuditFinished();
//         assertTrue("Checker.fireAuditFinished() doesn't call filter", f.wasCalled());

        // we do not call filter in fireFileStarted() (fix for 1080343)
//         f.resetFilter();
//         c.fireFileStarted("Some File Name");
//         assertTrue("Checker.fireFileStarted() doesn't call filter", f.wasCalled());

        // we do not call filter in fireFileFinished() (fix for 1080343)
//         f.resetFilter();
//         c.fireFileFinished("Some File Name");
//         assertTrue("Checker.fireFileFinished() doesn't call filter", f.wasCalled());

        f.resetFilter();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call filter", f.wasCalled());
    }

    @Test
    public void testRemoveFilter() throws Exception
    {
        final DebugChecker c = new DebugChecker();
        final DebugFilter f = new DebugFilter();
        final DebugFilter f2 = new DebugFilter();
        c.addFilter(f);
        c.addFilter(f2);
        c.removeFilter(f);

        // Let's try fire some events
        // we do call filter in fireErrors() only (fix for 1080343)
//      c.fireAuditStarted();
//         assertTrue("Checker.fireAuditStarted() doesn't call filter", f2.wasCalled());
//         assertFalse("Checker.fireAuditStarted() does call removed filter", f.wasCalled());

        // we do call filter in fireErrors() only (fix for 1080343)
//         f2.resetFilter();
//         c.fireAuditFinished();
//         assertTrue("Checker.fireAuditFinished() doesn't call filter", f2.wasCalled());
//         assertFalse("Checker.fireAuditFinished() does call removed filter", f.wasCalled());

        // we do call filter in fireErrors() only (fix for 1080343)
//         f2.resetFilter();
//         c.fireFileStarted("Some File Name");
//         assertTrue("Checker.fireFileStarted() doesn't call filter", f2.wasCalled());
//         assertFalse("Checker.fireFileStarted() does call removed filter", f.wasCalled());

        // we do call filter in fireErrors() only (fix for 1080343)
//         f2.resetFilter();
//         c.fireFileFinished("Some File Name");
//         assertTrue("Checker.fireFileFinished() doesn't call filter", f2.wasCalled());
//         assertFalse("Checker.fireFileFinished() does call removed filter", f.wasCalled());

        f2.resetFilter();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed filter", f.wasCalled());

    }
}

// Utility classes
class DebugChecker extends Checker
{
    public DebugChecker() throws CheckstyleException
    {
        super();
    }

    @Override
    public void fireAuditFinished()
    {
        super.fireAuditFinished();
    }

    @Override
    public void fireAuditStarted()
    {
        super.fireAuditStarted();
    }
}
class DebugAuditAdapter implements AuditListener
{
    /** keeps track of the number of errors */
    private boolean mCalled;

    public boolean wasCalled()
    {
        return mCalled;
    }

    public void resetListener()
    {
        mCalled = false;
    }

    /** @see AuditListener */
    public void addError(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    public void auditStarted(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    public void fileStarted(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    public void auditFinished(AuditEvent aEvt)
    {
        mCalled = true;
    }

    /** @see AuditListener */
    public void fileFinished(AuditEvent aEvt)
    {
        mCalled = true;
    }
}

class DebugFilter implements Filter
{
    private boolean mCalled;

    public boolean accept(AuditEvent aEvent)
    {
        mCalled = true;
        return true;
    }

    public boolean wasCalled()
    {
        return mCalled;
    }

    public void resetFilter()
    {
        mCalled = false;
    }
}
