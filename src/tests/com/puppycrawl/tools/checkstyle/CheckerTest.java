package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import java.io.File;
import org.junit.Test;

public class CheckerTest
{
    @Test
    public void testDosBasedir() throws Exception
    {
        Checker c = new Checker();

        c.setBasedir("c:/a\\b/./c\\..\\d");
        assertEquals("C:\\a\\b\\d", c.getBasedir());
    }

    @Test
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
        assertTrue((testinputs_dir + "coding").equalsIgnoreCase(c.getBasedir()));
    }

    @Test
    public void testDestroy() throws Exception
    {
        DebugChecker c= new DebugChecker();
        DebugAuditAdapter aa = new DebugAuditAdapter();
        c.addListener(aa);
        DebugFilter f = new DebugFilter();
        c.addFilter(f);

        c.destroy(); // should remove al listeners and filters

        // Let's try fire some events
        c.fireAuditStarted();
        c.fireAuditFinished();
        c.fireFileStarted("Some File Name");
        c.fireFileFinished("Some File Name");

        LocalizedMessage[] msgs = new LocalizedMessage[1];
        msgs[0] = new LocalizedMessage(0, 0, "a Bundle", "message.key",
                                       new Object[] {"arg"}, null,
                                       getClass());
        c.fireErrors("Some File Name", msgs);

        assertFalse("Checker.destroy() doesn't remove listeners.", aa.wasCalled());
        assertFalse("Checker.destroy() doesn't remove filters.", f.wasCalled());
    }

    @Test
    public void testAddListener() throws Exception
    {
        DebugChecker c= new DebugChecker();
        DebugAuditAdapter aa = new DebugAuditAdapter();
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
        LocalizedMessage[] msgs = new LocalizedMessage[1];
        msgs[0] = new LocalizedMessage(0, 0, "a Bundle", "message.key",
                                       new Object[] {"arg"}, null,
                                       getClass());
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call listener", aa.wasCalled());
    }

    @Test
    public void testRemoveListener() throws Exception
    {
        DebugChecker c= new DebugChecker();
        DebugAuditAdapter aa = new DebugAuditAdapter();
        DebugAuditAdapter aa2 = new DebugAuditAdapter();
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
        LocalizedMessage[] msgs = new LocalizedMessage[1];
        msgs[0] = new LocalizedMessage(0, 0, "a Bundle", "message.key",
                                       new Object[] {"arg"}, null,
                                       getClass());
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed listener", aa.wasCalled());

    }

    @Test
    public void testAddFilter() throws Exception
    {
        DebugChecker c= new DebugChecker();
        DebugFilter f = new DebugFilter();

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
        LocalizedMessage[] msgs = new LocalizedMessage[1];
        msgs[0] = new LocalizedMessage(0, 0, "a Bundle", "message.key",
                                       new Object[] {"arg"}, null,
                                       getClass());
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call filter", f.wasCalled());
    }

    @Test
    public void testRemoveFilter() throws Exception
    {
        DebugChecker c= new DebugChecker();
        DebugFilter f = new DebugFilter();
        DebugFilter f2 = new DebugFilter();
        c.addFilter(f);
        c.addFilter(f2);
        c.removeFilter(f);

        // Let's try fire some events
 	// we do call filter in fireErrors() only (fix for 1080343)
// 	c.fireAuditStarted();
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
        LocalizedMessage[] msgs = new LocalizedMessage[1];
        msgs[0] = new LocalizedMessage(0, 0, "a Bundle", "message.key",
                                       new Object[] {"arg"}, null,
                                       getClass());
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
