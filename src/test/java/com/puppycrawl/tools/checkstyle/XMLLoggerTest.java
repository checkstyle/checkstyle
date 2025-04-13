///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

/**
 * Enter a description of class XMLLoggerTest.java.
 */
// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XMLLoggerTest extends AbstractXmlTestSupport {

    /**
     * Output stream to hold the test results. The IntelliJ IDEA issues the AutoCloseableResource
     * warning here, so it needs to be suppressed. The {@code ByteArrayOutputStream} does not hold
     * any resources that need to be released.
     */
    private final CloseAndFlushTestByteArrayOutputStream outStream =
        new CloseAndFlushTestByteArrayOutputStream();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xmllogger";
    }

    @Test
    public void testEncode()
            throws IOException {
        final XMLLogger test = new XMLLogger(outStream, OutputStreamOptions.NONE);
        assertWithMessage("should be able to create XMLLogger without issue")
            .that(test)
            .isNotNull();
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
            assertWithMessage("\"" + encoding[0] + "\"")
                .that(encoded)
                .isEqualTo(encoding[1]);
        }
        outStream.close();
    }

    @Test
    public void testIsReference()
            throws IOException {
        final XMLLogger test = new XMLLogger(outStream, OutputStreamOptions.NONE);
        assertWithMessage("should be able to create XMLLogger without issue")
            .that(test)
            .isNotNull();
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
            assertWithMessage("reference: " + reference)
                    .that(XMLLogger.isReference(reference))
                    .isTrue();
        }
        final String[] noReferences = {
            "&",
            "&;",
            "&#;",
            "&#a;",
            "&#X0;",
            "&#x;",
            "&#xg;",
            "ramp;",
            "ref",
        };
        for (String noReference : noReferences) {
            assertWithMessage("no reference: " + noReference)
                    .that(XMLLogger.isReference(noReference))
                    .isFalse();
        }

        outStream.close();
    }

    @Test
    public void testCloseStream()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);

        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testNoCloseStream()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.NONE);
        logger.auditStarted(null);
        logger.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(0);

        outStream.close();
        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testFileStarted()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileStarted(ev);
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLogger.xml"), outStream);
    }

    @Test
    public void testFileFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLogger.xml"), outStream);
    }

    @Test
    public void testAddError() throws Exception {
        verifyWithInlineConfigParserAndXmlLogger("InputXMLLoggerAddError.java",
                "ExpectedXMLLoggerAddError.xml");
    }

    @Test
    public void testAddErrorWithNullFileName() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "key", null, SeverityLevel.ERROR, null,
                        getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, violation);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerErrorNullFileName.xml"), outStream,
                violation.getViolation());
    }

    @Test
    public void testAddErrorModuleId() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
            new Violation(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, "<i></i>-->",
                    getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerErrorModuleId.xml"), outStream,
                violation.getViolation());
    }

    @Test
    public void testAddErrorWithEncodedMessage() throws Exception {
        final String inputFileWithConfig = "InputXMLLoggerEncodedMessage.java";
        final String expectedXmlReport = "ExpectedXMLLoggerEncodedMessage.xml";
        verifyWithInlineConfigParserAndXmlLogger(inputFileWithConfig, expectedXmlReport);
    }

    @Test
    public void testAddErrorOnZeroColumns() throws Exception {
        verifyWithInlineConfigParserAndXmlLogger("InputXMLLoggerErrorOnZeroColumn.java",
                "ExpectedXMLLoggerErrorZeroColumn.xml");
    }

    @Test
    public void testAddIgnored() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "key", null, SeverityLevel.IGNORE, null,
                        getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addError(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerEmpty.xml"), outStream);
    }

    @Test
    public void testAddException()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
            new Violation(1, 1,
                "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException.xml"), outStream);
        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testAddExceptionWithNullFileName()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, violation);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerExceptionNullFileName.xml"), outStream);
        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testAddExceptionAfterFileStarted()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);

        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);

        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));

        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException2.xml"), outStream);
        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testAddExceptionBeforeFileFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        final AuditEvent fileFinishedEvent = new AuditEvent(this, "Test.java");
        logger.fileFinished(fileFinishedEvent);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException3.xml"), outStream);
        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testAddExceptionBetweenFileStartedAndFinished()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);
        logger.addException(ev, new TestException("msg", new RuntimeException("msg")));
        final AuditEvent fileFinishedEvent = new AuditEvent(this, "Test.java");
        logger.fileFinished(fileFinishedEvent);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerException2.xml"), outStream);
        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testAuditFinishedWithoutFileFinished() throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final AuditEvent fileStartedEvent = new AuditEvent(this, "Test.java");
        logger.fileStarted(fileStartedEvent);

        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", "key", null, SeverityLevel.ERROR, null,
                        getClass(), null);
        final AuditEvent errorEvent = new AuditEvent(this, "Test.java", violation);
        logger.addError(errorEvent);

        logger.fileFinished(errorEvent);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerError.xml"), outStream, violation.getViolation());
    }

    @Test
    public void testFileOpenTag()
            throws Exception {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final AuditEvent ev = new AuditEvent(this, "Test&.java");
        logger.fileFinished(ev);
        logger.auditFinished(null);
        verifyXml(getPath("ExpectedXMLLoggerSpecialName.xml"),
                outStream, "<file name=" + "Test&amp;.java" + ">");

    }

    @Test
    public void testVerifyLogger()
            throws Exception {
        final String inputFileWithConfig = "InputXMLLogger.java";
        final String xmlReport = "ExpectedXMLLoggerWithChecker.xml";
        verifyWithInlineConfigParserAndXmlLogger(inputFileWithConfig, xmlReport);
    }

    @Test
    public void testVerifyLoggerWithMultipleInput()
            throws Exception {
        final String inputFileWithConfig = "InputXMLLogger.java";
        final String expectedXmlReport = "ExpectedXMLLoggerDuplicatedFile.xml";
        verifyWithInlineConfigParserAndXmlLogger(
                inputFileWithConfig,
                expectedXmlReport,
                List.of(inputFileWithConfig, inputFileWithConfig));
    }

    @Test
    public void testNullOutputStreamOptions() {
        try {
            final XMLLogger logger = new XMLLogger(outStream,
                    (OutputStreamOptions) null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertWithMessage("Null instance")
                .that(logger)
                .isNotNull();
            assertWithMessage("Exception was expected").fail();
        }
        catch (IllegalArgumentException exception) {
            assertWithMessage("Invalid error message")
                .that(exception.getMessage())
                .isEqualTo("Parameter outputStreamOptions can not be null");
        }
    }

    @Test
    public void testFinishLocalSetup() {
        final XMLLogger logger = new XMLLogger(outStream, OutputStreamOptions.CLOSE);
        logger.finishLocalSetup();
        logger.auditStarted(null);
        logger.auditFinished(null);
        assertWithMessage("instance should not be null")
            .that(logger)
            .isNotNull();
    }

    /**
     * We keep this test for 100% coverage. Until #12873.
     */
    @Test
    public void testCtorWithTwoParametersCloseStreamOptions() {
        final XMLLogger logger = new XMLLogger(outStream, AutomaticBean.OutputStreamOptions.CLOSE);
        final boolean closeStream = TestUtil.getInternalState(logger, "closeStream");

        assertWithMessage("closeStream should be true")
                .that(closeStream)
                .isTrue();
    }

    /**
     * We keep this test for 100% coverage. Until #12873.
     */
    @Test
    public void testCtorWithTwoParametersNoneStreamOptions() {
        final XMLLogger logger = new XMLLogger(outStream, AutomaticBean.OutputStreamOptions.NONE);
        final boolean closeStream = TestUtil.getInternalState(logger, "closeStream");

        assertWithMessage("closeStream should be false")
                .that(closeStream)
                .isFalse();
    }

    private static final class TestException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private TestException(String msg, Throwable cause) {
            super(msg, cause);
        }

        @Override
        public void printStackTrace(PrintWriter printWriter) {
            printWriter.print("stackTrace\r\nexample");
        }

    }

}
