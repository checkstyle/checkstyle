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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class CheckerTest {
    @Test
    public void testDestroy() throws Exception {
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
    public void testAddListener() throws Exception {
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
    public void testRemoveListener() throws Exception {
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
    public void testAddFilter() throws Exception {
        final DebugChecker c = new DebugChecker();
        final DebugFilter f = new DebugFilter();

        c.addFilter(f);

        f.resetFilter();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call filter", f.wasCalled());
    }

    @Test
    public void testRemoveFilter() throws Exception {
        final DebugChecker c = new DebugChecker();
        final DebugFilter f = new DebugFilter();
        final DebugFilter f2 = new DebugFilter();
        c.addFilter(f);
        c.addFilter(f2);
        c.removeFilter(f);

        f2.resetFilter();
        final TreeSet<LocalizedMessage> msgs = Sets.newTreeSet();
        msgs.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        c.fireErrors("Some File Name", msgs);
        assertTrue("Checker.fireErrors() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed filter", f.wasCalled());

    }

    @Test
    public void testFileExtensions() throws Exception {
        final Checker c = new Checker();
        final List<File> files = new ArrayList<>();
        File f = new File("file.pdf");
        files.add(f);
        f = new File("file.java");
        files.add(f);
        final String[] fileExtensions = {"java", "xml", "properties"};
        c.setFileExtensions(fileExtensions);
        final int counter = c.process(files);
        assertEquals(1, counter); // comparing to 1 as there is only one legal file in input
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSetters() throws Exception {
        // all  that is set by reflection, so just make code coverage be happy
        final Checker c = new Checker();
        c.setClassLoader(this.getClass().getClassLoader());
        c.setClassloader(this.getClass().getClassLoader());
        c.setBasedir("some");
        c.setSeverity("ignore");

        PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        c.setModuleFactory(factory);

        c.setFileExtensions((String[]) null);
        c.setFileExtensions(new String[]{".java", "xml"});

        try {
            c.setCharset("UNKNOW-CHARSET");
            fail("Exception is expected");
        }
        catch (UnsupportedEncodingException ex) {
            assertEquals("unsupported charset: 'UNKNOW-CHARSET'", ex.getMessage());
        }
    }

    @Test
    public void testNoClassLoaderNoModuleFactory() throws Exception {
        final Checker c = new Checker();

        try {
            c.finishLocalSetup();
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("if no custom moduleFactory is set, "
                            + "moduleClassLoader must be specified", ex.getMessage());
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker c = new Checker();
        c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        c.finishLocalSetup();
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() throws Exception {
        final Checker c = new Checker();
        c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        c.setModuleFactory(factory);

        c.finishLocalSetup();
    }

    @Test
    public void testSetupChildExceptions() throws Exception {
        final Checker c = new Checker();
        PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        c.setModuleFactory(factory);

        Configuration config = new DefaultConfiguration("java.lang.String");
        try {
            c.setupChild(config);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module java.lang.String "
                    + "- java.lang.String is not allowed as a child in Checker", ex.getMessage());
        }
    }

    @Test
    public void testSetupChildListener() throws Exception {
        final Checker c = new Checker();
        PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        c.setModuleFactory(factory);

        Configuration config = new DefaultConfiguration(DebugAuditAdapter.class.getCanonicalName());
        c.setupChild(config);
    }
}
