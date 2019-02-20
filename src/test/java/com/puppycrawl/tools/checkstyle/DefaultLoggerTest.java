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

package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class DefaultLoggerTest {

    private final LocalizedMessage auditStartMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted", null, null,
            getClass(), null);

    private final LocalizedMessage auditFinishMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished", null, null,
            getClass(), null);

    @Test
    public void testCtor() throws UnsupportedEncodingException {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, true, errorStream, true);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString(StandardCharsets.UTF_8.name());
        final LocalizedMessage addExceptionMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.ADD_EXCEPTION_MESSAGE,
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

    @Test
    public void testNewCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.NONE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        assertTrue("Message should contain exception info, but was " + infoStream,
                infoStream.toString().contains("java.lang.IllegalStateException: upsss"));
    }

    @Test
    public void testNullInfoStreamOptions() {
        try {
            final DefaultLogger logger = new DefaultLogger(new ByteArrayOutputStream(), null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertNotNull("Null instance", logger);
            fail("Exception was expected");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Invalid error message", "Parameter infoStreamOptions can not be null",
                    exception.getMessage());
        }
    }

    @Test
    public void testNullErrorStreamOptions() {
        try {
            final DefaultLogger logger = new DefaultLogger(new ByteArrayOutputStream(),
                AutomaticBean.OutputStreamOptions.CLOSE, new ByteArrayOutputStream(), null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertNotNull("Null instance", logger);
            fail("Exception was expected");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Invalid error message", "Parameter errorStreamOptions can not be null",
                    exception.getMessage());
        }
    }

    @Test
    public void testAddError() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE, errorStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new LocalizedMessage(1, 2, "bundle", "key",
                null, null, getClass(), "customMessage")));
        dl.auditFinished(null);
        assertEquals("expected output", auditStartMessage.getMessage() + System.lineSeparator()
                + auditFinishMessage.getMessage() + System.lineSeparator(), infoStream.toString());
        assertEquals("expected output", "[ERROR] fileName:1:2: customMessage [DefaultLoggerTest]"
                + System.lineSeparator(), errorStream.toString());
    }

    @Test
    public void testAddErrorModuleId() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, true, errorStream, true);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new LocalizedMessage(1, 2, "bundle", "key",
                null, "moduleId", getClass(), "customMessage")));
        dl.auditFinished(null);
        assertEquals("expected output", auditStartMessage.getMessage() + System.lineSeparator()
                + auditFinishMessage.getMessage() + System.lineSeparator(), infoStream.toString());
        assertEquals("expected output", "[ERROR] fileName:1:2: customMessage [moduleId]"
                + System.lineSeparator(), errorStream.toString());
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.auditFinished(null);
        assertNotNull("instance should not be null", dl);
    }

}
