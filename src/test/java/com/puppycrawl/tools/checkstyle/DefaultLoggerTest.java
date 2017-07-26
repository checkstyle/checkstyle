////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class DefaultLoggerTest {

    @Test
    public void testCtor() throws UnsupportedEncodingException {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, true, errorStream, true);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString("UTF-8");
        final LocalizedMessage addExceptionMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.addException",
                new String[] {"myfile"}, null,
                getClass(), null);

        assertTrue("Invalid exception", output.contains(addExceptionMessage.getMessage()));
        assertTrue("Invalid exception class",
            output.contains("java.lang.IllegalStateException: upsss"));
    }

    @Test
    public void testCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, true);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = infoStream.toString();
        assertTrue("Message should contain exception info, but was " + output,
                output.contains("java.lang.IllegalStateException: upsss"));
    }
}
