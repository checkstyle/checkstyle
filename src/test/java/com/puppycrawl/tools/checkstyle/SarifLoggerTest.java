////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;

public class SarifLoggerTest extends AbstractModuleTestSupport {

    /**
     * Output stream to hold the test results. The IntelliJ IDEA issues the AutoCloseableResource
     * warning here, so it need to be suppressed. The {@code ByteArrayOutputStream} does not hold
     * any resources that need to be released.
     */
    private final CloseAndFlushTestByteArrayOutputStream outStream =
        new CloseAndFlushTestByteArrayOutputStream();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/sariflogger";
    }

    @Test
    public void testEscape() {
        final String[][] encodings = {
            {"\"", "\\\""},
            {"\\", "\\\\"},
            {"\b", "\\b"},
            {"\f", "\\f"},
            {"\n", "\\n"},
            {"\r", "\\r"},
            {"\t", "\\t"},
            {"/", "\\/"},
            {"\u0010", "\\u0010"},
            {"\u001E", "\\u001E"},
            {"\u001F", "\\u001F"},
            {" ", " "},
            {"bar1234", "bar1234"},
        };
        for (String[] encoding : encodings) {
            final String encoded = SarifLogger.escape(encoding[0]);
            assertEquals(encoding[1], encoded, "\"" + encoding[0] + "\"");
        }
    }

    @Test
    public void testAddError() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "ruleId", null, SeverityLevel.ERROR, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.fileStarted(ev);
        logger.addError(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerSingleError.sarif"), outStream);
    }

    @Test
    public void testAddErrorWithWarningLevel() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "ruleId", null, SeverityLevel.WARNING, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.fileStarted(ev);
        logger.addError(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerSingleWarning.sarif"), outStream);
    }

    @Test
    public void testAddErrors() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "ruleId", null, SeverityLevel.INFO, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        final Violation violation2 =
                new Violation(1, 1,
                        "messages.properties", "ruleId2", null, SeverityLevel.IGNORE, null,
                        getClass(), "found another error");
        final AuditEvent ev2 = new AuditEvent(this, "Test.java", violation2);
        logger.fileStarted(ev);
        logger.addError(ev);
        logger.fileFinished(ev);
        logger.fileStarted(ev2);
        logger.addError(ev2);
        logger.fileFinished(ev2);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerDoubleError.sarif"), outStream);
    }

    @Test
    public void testAddException() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation message =
                new Violation(1, 1,
                        "messages.properties", "null", null, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, null, message);
        logger.fileStarted(ev);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerSingleException.sarif"), outStream);
    }

    @Test
    public void testAddExceptions() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "null", null, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, null, violation);
        final Violation violation2 =
                new Violation(1, 1,
                        "messages.properties", "null", null, null,
                        getClass(), "found an error");
        final AuditEvent ev2 = new AuditEvent(this, "Test.java", violation2);
        logger.fileStarted(ev);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.fileFinished(ev);
        logger.fileStarted(ev2);
        logger.addException(ev2, new TestException("msg2", new RuntimeException("msg2")));
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerDoubleException.sarif"), outStream);
    }

    @Test
    public void testLineOnly() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
            AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
            new Violation(1, 0,
                "messages.properties", "ruleId", null, null,
                getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.fileStarted(ev);
        logger.addError(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerLineOnly.sarif"), outStream);
    }

    @Test
    public void testEmpty() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "null", null, null,
                        getClass(), "found an error");
        final AuditEvent ev = new AuditEvent(this, null, violation);
        logger.fileStarted(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyContent(getPath("ExpectedSarifLoggerEmpty.sarif"), outStream);
    }

    @Test
    public void testNullOutputStreamOptions() {
        try {
            final SarifLogger logger = new SarifLogger(outStream, null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertNotNull(logger, "Null instance");
            fail("Exception was expected");
        }
        catch (IllegalArgumentException | IOException exception) {
            assertEquals("Parameter outputStreamOptions can not be null",
                    exception.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testCloseStream() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertEquals(1, outStream.getCloseCount(), "Invalid close count");

        verifyContent(getPath("ExpectedSarifLoggerEmpty.sarif"), outStream);
    }

    @Test
    public void testNoCloseStream() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.NONE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertEquals(0, outStream.getCloseCount(), "Invalid close count");
        assertEquals(1, outStream.getFlushCount(), "Invalid flush count");

        outStream.close();
        verifyContent(getPath("ExpectedSarifLoggerEmpty.sarif"), outStream);
    }

    @Test
    public void testFinishLocalSetup() throws IOException {
        final SarifLogger logger = new SarifLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.finishLocalSetup();
        logger.auditStarted(null);
        logger.auditFinished(null);
        assertNotNull(logger, "instance should not be null");
    }

    @Test
    public void testReadResourceWithInvalidName() {
        try {
            SarifLogger.readResource("random");
            fail("Exception expected");
        }
        catch (IOException exception) {
            assertEquals("Cannot find the resource random",
                exception.getMessage(),
                "Exception message must match");
        }
    }

    private static void verifyContent(
            String expectedOutputFile,
            ByteArrayOutputStream actualOutputStream) throws IOException {
        final String expectedContent = readFile(expectedOutputFile);
        final String actualContent = toLfLineEnding(new String(actualOutputStream.toByteArray(),
                StandardCharsets.UTF_8));
        assertEquals(expectedContent, actualContent, "sarif content should match");
    }

    private static class TestException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        /* package */ TestException(String msg, Throwable cause) {
            super(msg, cause);
        }

        @Override
        public void printStackTrace(PrintWriter printWriter) {
            printWriter.print("stackTrace\nexample");
        }
    }
}
