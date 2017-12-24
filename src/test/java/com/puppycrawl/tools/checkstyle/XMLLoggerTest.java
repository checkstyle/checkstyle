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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;

/**
 * Enter a description of class XMLLoggerTest.java.
 * @author Rick Giles
 */
// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XMLLoggerTest extends AbstractXmlTestSupport {

    private final CloseAndFlushTestByteArrayOutputStream outStream =
        new CloseAndFlushTestByteArrayOutputStream();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xmllogger";
    }

    @Test
    public void testEncode()
            throws IOException {
        final XMLLogger test = new XMLLogger(outStream, false);
        assertNotNull("should be able to create XMLLogger without issue", test);
        final String[][] encodings = {
            {"<", "&lt;"},
            {">", "&gt;"},
            {"'", "&apos;"},
            {"\"", "&quot;"},
            {"&", "&amp;"},
            {"&lt;", "&amp;lt;"},
            {"abc;", "abc;"},
            {"&#0;", "&amp;#0;"},
            {"&#0", "&amp;#0"},
            {"&#X0;", "&amp;#X0;"},
            {"\u0001", "#x1;"},
            {"\u0080", "#x80;"},
        };
        for (String[] encoding : encodings) {
            final String encoded = XMLLogger.encode(encoding[0]);
            assertEquals("\"" + encoding[0] + "\"", encoding[1], encoded);
        }
        outStream.close();
    }

    @Test
    public void testIsReference()
            throws IOException {
        final XMLLogger test = new XMLLogger(outStream, false);
        assertNotNull("should be able to create XMLLogger without issue", test);
        final String[] references = {
            "&#0;",
            "&#x0;",
            "&lt;",
            "&gt;",
            "&apos;",
            "&quot;",
            "&amp;",
        };
        for (String reference : references) {
            assertTrue("reference: " + reference,
                    XMLLogger.isReference(reference));
        }
        final String[] noReferences = {
            "&",
            "&;",
            "&#;",
            "&#a;",
            "&#X0;",
            "&#x;",
            "&#xg;",
            "ref",
        };
        for (String noReference : noReferences) {
            assertFalse("no reference: " + noReference,
                    XMLLogger.isReference(noReference));
        }

        outStream.close();
    }

    @Test
    public void testCloseStream()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertEquals("Invalid close count", 1, outStream.getCloseCount());

        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testNoCloseStream()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream,
                AutomaticBean.OutputStreamOptions.NONE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertEquals("Invalid close count", 0, outStream.getCloseCount());

        outStream.close();
        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testFileStarted()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileStarted(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLogger.xml"), outStream);
    }

    @Test
    public void testFileFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLogger.xml"), outStream);
    }

    @Test
    public void testAddError() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                    getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerError.xml"), outStream, message.getMessage());
    }

    @Test
    public void testAddErrorWithNullFileName() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", "key", null, SeverityLevel.ERROR, null,
                        getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerErrorNullFileName.xml"), outStream,
                message.getMessage());
    }

    @Test
    public void testAddErrorModuleId() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, "module",
                    getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerErrorModuleId.xml"), outStream, message.getMessage());
    }

    @Test
    public void testAddErrorOnZeroColumns() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 0,
                        "messages.properties", "key", null, SeverityLevel.ERROR, null,
                        getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerErrorZeroColumn.xml"), outStream,
                message.getMessage());
    }

    @Test
    public void testAddIgnored() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", "key", null, SeverityLevel.IGNORE, null,
                        getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testAddException()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException.xml"), outStream);
        assertEquals("Invalid close count", 1, outStream.getCloseCount());
    }

    @Test
    public void testAddExceptionWithNullFileName()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerExceptionNullFileName.xml"), outStream);
        assertEquals("Invalid close count", 1, outStream.getCloseCount());
    }

    @Test
    public void testAddExceptionAfterFileStarted()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);

        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);

        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));

        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException.xml"), outStream);
        assertEquals("Invalid close count", 1, outStream.getCloseCount());
    }

    @Test
    public void testAddExceptionBeforeFileFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        final AuditEvent fileFinishedEvent = new AuditEvent(this, "Test.java");
        logger.fileFinished(fileFinishedEvent);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException.xml"), outStream);
        assertEquals("Invalid close count", 1, outStream.getCloseCount());
    }

    @Test
    public void testAddExceptionBetweenFileStartedAndFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);
        final AuditEvent ev = new AuditEvent(this, "Test.java", message);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        final AuditEvent fileFinishedEvent = new AuditEvent(this, "Test.java");
        logger.fileFinished(fileFinishedEvent);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException.xml"), outStream);
        assertEquals("Invalid close count", 1, outStream.getCloseCount());
    }

    @Test
    public void testAuditFinishedWithoutFileFinished() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.auditStarted(null);
        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);

        final LocalizedMessage message =
                new LocalizedMessage(1, 1,
                        "messages.properties", "key", null, SeverityLevel.ERROR, null,
                        getClass(), null);
        final AuditEvent errorEvent = new AuditEvent(this, "Test.java", message);
        logger.addError(errorEvent);

        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerError.xml"), outStream, message.getMessage());
    }

    @Test
    public void testFinishLocalSetup() throws CheckstyleException {
        final XMLLogger logger = new XMLLogger(outStream, true);
        logger.finishLocalSetup();
        logger.auditStarted(null);
        logger.auditFinished(null);
        assertNotNull("instance should not be null", logger);
    }

    private static class TestException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        TestException(String msg, Throwable cause) {
            super(msg, cause);
        }

        @Override
        public void printStackTrace(PrintWriter printWriter) {
            printWriter.print("stackTrace\r\nexample");
        }

    }

}
