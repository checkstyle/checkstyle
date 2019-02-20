////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SuppressWarningsHolder.class)
public class SuppressWarningsHolderPowerTest {

    @Test
    public void testIsSuppressed() throws Exception {
        createHolder("MockEntry", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 10);

        assertFalse("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedByName() throws Exception {
        final SuppressWarningsHolder holder = createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("id", 110, 10);
        holder.setAliasList(MemberNameCheck.class.getName() + "=check");

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedByModuleId() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 350);

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedAfterEventEnd() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 352);

        assertFalse("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedAfterEventEnd2() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 400, 10);

        assertFalse("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedAfterEventStart() throws Exception {
        createHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 100);

        assertTrue("Event is not suppressed", SuppressWarningsHolder.isSuppressed(event));
    }

    @Test
    public void testIsSuppressedWithAllArgument() throws Exception {
        createHolder("all", 100, 100, 350, 350);

        final Checker source = new Checker();
        final LocalizedMessage firstMessageForTest =
            new LocalizedMessage(100, 10, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent firstEventForTest =
            new AuditEvent(source, "fileName", firstMessageForTest);
        assertFalse("Event is suppressed",
                SuppressWarningsHolder.isSuppressed(firstEventForTest));

        final LocalizedMessage secondMessageForTest =
            new LocalizedMessage(100, 150, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent secondEventForTest =
            new AuditEvent(source, "fileName", secondMessageForTest);
        assertTrue("Event is not suppressed",
                SuppressWarningsHolder.isSuppressed(secondEventForTest));

        final LocalizedMessage thirdMessageForTest =
            new LocalizedMessage(200, 1, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent thirdEventForTest =
            new AuditEvent(source, "fileName", thirdMessageForTest);
        assertTrue("Event is not suppressed",
                SuppressWarningsHolder.isSuppressed(thirdEventForTest));
    }

    private static SuppressWarningsHolder createHolder(String checkName, int firstLine,
                                                       int firstColumn, int lastLine,
                                                       int lastColumn) throws Exception {
        final Class<?> entry = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder$Entry");
        final Constructor<?> entryConstr = entry.getDeclaredConstructor(String.class, int.class,
                int.class, int.class, int.class);
        entryConstr.setAccessible(true);

        final Object entryInstance = entryConstr.newInstance(checkName, firstLine,
                firstColumn, lastLine, lastColumn);

        final List<Object> entriesList = new ArrayList<>();
        entriesList.add(entryInstance);

        final ThreadLocal<?> threadLocal = mock(ThreadLocal.class);
        PowerMockito.doReturn(entriesList).when(threadLocal, "get");

        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Field entries = holder.getClass().getDeclaredField("ENTRIES");
        entries.setAccessible(true);
        entries.set(holder, threadLocal);
        return holder;
    }

    private static AuditEvent createAuditEvent(String moduleId, int line, int column) {
        final Checker source = new Checker();
        final LocalizedMessage message = new LocalizedMessage(line, column, null, null, null,
                moduleId, MemberNameCheck.class, "message");
        return new AuditEvent(source, "filename", message);
    }

}
