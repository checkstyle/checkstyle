///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class ChecksAndFilesSuppressionFileGeneratorAuditListenerTest {

    /** OS specific line separator. */
    private static final String EOL = System.getProperty("line.separator");

    private static final String SUPPRESSION_XML_HEADER =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionFilter Configuration 1.2//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2.dtd\">" + EOL;

    private static final Violation FIRST_MESSAGE = createViolation(3, 51,
            TokenTypes.LCURLY, null, LeftCurlyCheck.class);

    private static final Violation SECOND_MESSAGE = createViolation(5, 5,
            TokenTypes.VARIABLE_DEF, "JavadocModuleId", JavadocVariableCheck.class);

    private static final Violation THIRD_MESSAGE = createViolation(7, 5,
            TokenTypes.METHOD_DEF, "MyModule", MethodParamPadCheck.class);

    private final CloseAndFlushTestByteArrayOutputStream outStream =
            new CloseAndFlushTestByteArrayOutputStream();

    private static Violation createViolation(int lineNumber,
                        int columnNumber, int tokenType,
                        String moduleId,
                        Class<?> sourceClass) {

        return new Violation(lineNumber, columnNumber, tokenType,
            "messages.properties", null, null,
            SeverityLevel.ERROR, moduleId, sourceClass, null);
    }

    /**
     * Verifies the fileStarted method separately to give empty output
     * which is not possible to get from the CLI run test.
     */
    @Test
    public void testFileStarted() {
        final OutputStream out = new ByteArrayOutputStream();
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(
                        out, OutputStreamOptions.CLOSE);
        final AuditEvent ev = new AuditEvent(this, "Test.java", null);
        listener.fileStarted(ev);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    /**
     * Verifies the fileFinished method separately to give empty output.
     */
    @Test
    public void testFileFinished() {
        final OutputStream out = new ByteArrayOutputStream();
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(out,
                        OutputStreamOptions.CLOSE);
        final AuditEvent ev = new AuditEvent(this, "Test.java", null);
        listener.fileFinished(ev);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    /**
     * Verifies the addException method.
     *
     * @throws UnsupportedOperationException this method is not supported
     *     in {@link ChecksAndFilesSuppressionFileGeneratorAuditListener}.
     */
    @Test
    public void testAddException() {
        final OutputStream out = new ByteArrayOutputStream();
        final ChecksAndFilesSuppressionFileGeneratorAuditListener logger =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(out,
                        OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);

        try {
            logger.addException(ev, null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    /**
     * Verifies the close count through {@link CloseAndFlushTestByteArrayOutputStream}
     * which is not supported by OutputStreams uses in 'Main.java'.
     */
    @Test
    public void testCloseStream() {
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(outStream,
                OutputStreamOptions.CLOSE);
        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    /**
     * Verifies the close count through {@link CloseAndFlushTestByteArrayOutputStream}.
     */
    @Test
    public void testNoCloseStream() {
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(outStream,
                        OutputStreamOptions.NONE);
        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(0);
    }

    @Test
    public void testCorrectOne() {
        final AuditEvent event1 = createAuditEvent(
                "InputChecksAndFilesSuppressionFileGeneratorAuditListener.java", FIRST_MESSAGE);
        final AuditEvent event2 = createAuditEvent(
                "InputChecksAndFilesSuppressionFileGeneratorAuditListener.java", SECOND_MESSAGE);

        final String expected = SUPPRESSION_XML_HEADER
                + "<suppressions>" + EOL
                + "  <suppress" + EOL
                + "      files=\"InputChecksAndFilesSuppressionFileGeneratorAuditListener.java\""
                + EOL
                + "      checks=\"LeftCurlyCheck\""
                + "/>" + EOL
                + "  <suppress" + EOL
                + "      files=\"InputChecksAndFilesSuppressionFileGeneratorAuditListener.java\""
                + EOL
                + "      id=\"JavadocModuleId\""
                + "/>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event1, event2);
    }

    @Test
    public void testCorrectTwo() {
        final AuditEvent event1 = createAuditEvent(
                "InputChecksAndFilesSuppressionFileGeneratorAuditListener.java",
                5, 5, JavadocVariableCheck.class);

        final AuditEvent event2 = createAuditEvent(
                "InputChecksAndFilesSuppressionFileGeneratorAuditListener.java", THIRD_MESSAGE);

        final String expected = SUPPRESSION_XML_HEADER
                + "<suppressions>" + EOL
                + "  <suppress" + EOL
                + "      files=\"InputChecksAndFilesSuppressionFileGeneratorAuditListener.java\""
                + EOL
                + "      checks=\"JavadocVariableCheck\""
                + "/>" + EOL
                + "  <suppress" + EOL
                + "      files=\"InputChecksAndFilesSuppressionFileGeneratorAuditListener.java\""
                + EOL
                + "      id=\"MyModule\""
                + "/>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event1, event2);
    }

    @Test
    public void testFileNameNullCase() {
        final AuditEvent event1 = new AuditEvent(this, "/", FIRST_MESSAGE);

        final String expected = SUPPRESSION_XML_HEADER
                + "<suppressions>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event1);
    }

    /**
     * Verifies the finishLocalSetup method separately to give empty output.
     */
    @Test
    public void testFinishLocalSetup() {
        final OutputStream out = new ByteArrayOutputStream();
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                new ChecksAndFilesSuppressionFileGeneratorAuditListener(out,
                        OutputStreamOptions.CLOSE);

        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    @Test
    public void testNullOutputStreamOptions() {
        final OutputStream out = new ByteArrayOutputStream();
        try {
            final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
                    new ChecksAndFilesSuppressionFileGeneratorAuditListener(out,
                            null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertWithMessage("Null instance")
                    .that(listener)
                    .isNotNull();
            assertWithMessage("Exception was expected").fail();
        }
        catch (IllegalArgumentException exception) {
            assertWithMessage("Invalid error message")
                    .that(exception.getMessage())
                    .isEqualTo("Parameter outputStreamOptions can not be null");
        }
    }

    private AuditEvent createAuditEvent(String fileName, int lineNumber, int columnNumber,
                                        Class<?> sourceClass) {
        final Violation violation =
                new Violation(lineNumber, columnNumber, "messages.properties", null,
                        null, null, sourceClass, null);

        return new AuditEvent(this,
                getPath(fileName), violation);
    }

    private AuditEvent createAuditEvent(String fileName, Violation violation) {
        return new AuditEvent(this,
                getPath(fileName), violation);
    }

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "checksandfilessuppressionfilegeneratorauditlistener/"
                + filename;
    }

    private void verifyOutput(String expected, AuditEvent... events) {
        final ChecksAndFilesSuppressionFileGeneratorAuditListener listener =
            new ChecksAndFilesSuppressionFileGeneratorAuditListener(outStream,
                OutputStreamOptions.CLOSE);

        for (AuditEvent event : events) {
            listener.addError(event);
        }

        listener.auditFinished(null);

        assertWithMessage("Output stream flush count")
            .that(outStream.getFlushCount())
            .isEqualTo(TestUtil.adjustFlushCountForOutputStreamClose(1));
        assertWithMessage("Output stream close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);

        final String actual = outStream.toString(StandardCharsets.UTF_8);
        assertWithMessage("Invalid suppressions file content")
            .that(actual)
            .isEqualTo(expected);
    }
}
