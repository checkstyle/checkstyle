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
import java.util.SortedSet;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class CheckerTest {
    @Test
    public void testDestroy() throws Exception {
        final DebugChecker checker = new DebugChecker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);
        final DebugFilter filter = new DebugFilter();
        checker.addFilter(filter);

        // should remove al listeners and filters
        checker.destroy();

        // Let's try fire some events
        checker.fireAuditStarted();
        checker.fireAuditFinished();
        checker.fireFileStarted("Some File Name");
        checker.fireFileFinished("Some File Name");

        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);

        assertFalse("Checker.destroy() doesn't remove listeners.", auditAdapter.wasCalled());
        assertFalse("Checker.destroy() doesn't remove filters.", filter.wasCalled());
    }

    @Test
    public void testAddListener() throws Exception {
        final DebugChecker checker = new DebugChecker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        // Let's try fire some events
        checker.fireAuditStarted();
        assertTrue("Checker.fireAuditStarted() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        checker.fireAuditFinished();
        assertTrue("Checker.fireAuditFinished() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call listener", auditAdapter.wasCalled());
    }

    @Test
    public void testRemoveListener() throws Exception {
        final DebugChecker checker = new DebugChecker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        final DebugAuditAdapter aa2 = new DebugAuditAdapter();
        checker.addListener(auditAdapter);
        checker.addListener(aa2);
        checker.removeListener(auditAdapter);

        // Let's try fire some events
        checker.fireAuditStarted();
        assertTrue("Checker.fireAuditStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditStarted() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        checker.fireAuditFinished();
        assertTrue("Checker.fireAuditFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditFinished() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileStarted() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileFinished() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed listener", auditAdapter.wasCalled());

    }

    @Test
    public void testAddFilter() throws Exception {
        final DebugChecker checker = new DebugChecker();
        final DebugFilter filter = new DebugFilter();

        checker.addFilter(filter);

        filter.resetFilter();
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call filter", filter.wasCalled());
    }

    @Test
    public void testRemoveFilter() throws Exception {
        final DebugChecker checker = new DebugChecker();
        final DebugFilter filter = new DebugFilter();
        final DebugFilter f2 = new DebugFilter();
        checker.addFilter(filter);
        checker.addFilter(f2);
        checker.removeFilter(filter);

        f2.resetFilter();
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed filter", filter.wasCalled());

    }

    @Test
    public void testFileExtensions() throws Exception {
        final Checker checker = new Checker();
        final List<File> files = new ArrayList<>();
        final File file = new File("file.pdf");
        files.add(file);
        final File otherFile = new File("file.java");
        files.add(otherFile);
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        final int counter = checker.process(files);

        // comparing to 1 as there is only one legal file in input
        assertEquals(1, counter);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSetters() throws Exception {
        // all  that is set by reflection, so just make code coverage be happy
        final Checker checker = new Checker();
        checker.setClassLoader(getClass().getClassLoader());
        checker.setClassloader(getClass().getClassLoader());
        checker.setBasedir("some");
        checker.setSeverity("ignore");

        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        checker.setFileExtensions((String[]) null);
        checker.setFileExtensions(".java", "xml");

        try {
            checker.setCharset("UNKNOWN-CHARSET");
            fail("Exception is expected");
        }
        catch (UnsupportedEncodingException ex) {
            assertEquals("unsupported charset: 'UNKNOWN-CHARSET'", ex.getMessage());
        }
    }

    @Test
    public void testNoClassLoaderNoModuleFactory() throws Exception {
        final Checker checker = new Checker();

        try {
            checker.finishLocalSetup();
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("if no custom moduleFactory is set, "
                            + "moduleClassLoader must be specified", ex.getMessage());
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        checker.finishLocalSetup();
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() throws Exception {
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        checker.finishLocalSetup();
    }

    @Test
    public void testSetupChildExceptions() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        final Configuration config = new DefaultConfiguration("java.lang.String");
        try {
            checker.setupChild(config);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("java.lang.String is not allowed as a child in Checker", ex.getMessage());
        }
    }

    @Test
    public void testSetupChildListener() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<String>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        final Configuration config = new DefaultConfiguration(
            DebugAuditAdapter.class.getCanonicalName());
        checker.setupChild(config);
    }
}
