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
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class XpathFileGeneratorAuditListenerTest {

    /** OS specific line separator. */
    private static final String EOL = System.getProperty("line.separator");

    private static final Violation FIRST_MESSAGE = createViolation(3, 51,
            TokenTypes.LCURLY, null, LeftCurlyCheck.class);

    private static final Violation SECOND_MESSAGE = createViolation(15, 5,
            TokenTypes.METHOD_DEF, "MyModule", MethodParamPadCheck.class);

    private static final Violation THIRD_MESSAGE = createViolation(17, 13,
            TokenTypes.LITERAL_FOR, null, NestedForDepthCheck.class);

    private static final Violation FOURTH_MESSAGE = createViolation(5, 5,
            TokenTypes.VARIABLE_DEF, "JavadocModuleId", JavadocVariableCheck.class);

    private final CloseAndFlushTestByteArrayOutputStream outStream =
            new CloseAndFlushTestByteArrayOutputStream();

    static {
        try {
            constructEvents();
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void constructEvents() throws Exception {
        final TreeWalkerAuditEvent event1 = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAuditListener.java", FIRST_MESSAGE);

        final TreeWalkerAuditEvent event2 = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAuditListener.java", SECOND_MESSAGE);

        final TreeWalkerAuditEvent event3 = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAuditListener.java", THIRD_MESSAGE);

        final TreeWalkerAuditEvent event4 = createTreeWalkerAuditEvent(
                "InputXpathFileGeneratorAuditListener.java", FOURTH_MESSAGE);

        final XpathFileGeneratorAstFilter astFilter = new XpathFileGeneratorAstFilter();
        astFilter.accept(event1);
        astFilter.accept(event2);
        astFilter.accept(event3);
        astFilter.accept(event4);
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream out = new ByteArrayOutputStream();
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(out, OutputStreamOptions.CLOSE);

        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    @Test
    public void testFileStarted() {
        final OutputStream out = new ByteArrayOutputStream();
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(out, OutputStreamOptions.CLOSE);
        final AuditEvent ev = new AuditEvent(this, "Test.java", null);
        listener.fileStarted(ev);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    @Test
    public void testFileFinished() {
        final OutputStream out = new ByteArrayOutputStream();
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(out, OutputStreamOptions.CLOSE);
        final AuditEvent ev = new AuditEvent(this, "Test.java", null);
        listener.fileFinished(ev);
        listener.auditFinished(null);
        final String actual = out.toString();
        assertWithMessage("Output should be empty")
            .that(actual)
            .isEmpty();
    }

    @Test
    public void testAddException() {
        final OutputStream out = new ByteArrayOutputStream();
        final XpathFileGeneratorAuditListener logger =
                new XpathFileGeneratorAuditListener(out, OutputStreamOptions.CLOSE);
        logger.auditStarted(null);
        final Violation violation =
                new Violation(1, 1,
                        "messages.properties", null, null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "Test.java", violation);

        try {
            logger.addException(ev, null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testCorrectOne() {
        final AuditEvent event = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                FIRST_MESSAGE);

        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputXpathFileGeneratorAuditListener.java\"" + EOL
                + "       checks=\"LeftCurlyCheck\""
                + EOL
                + "       query=\"/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathFileGeneratorAuditListener']]"
                + "/OBJBLOCK/LCURLY\"/>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event);
    }

    @Test
    public void testCorrectTwo() {
        final AuditEvent event1 = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                SECOND_MESSAGE);

        final AuditEvent event2 = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                THIRD_MESSAGE);

        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputXpathFileGeneratorAuditListener.java\"" + EOL
                + "       id=\"MyModule\"" + EOL
                + "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathFileGeneratorAuditListener']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='sort']]\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputXpathFileGeneratorAuditListener.java\"" + EOL
                + "       checks=\"NestedForDepthCheck\"" + EOL
                + "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathFileGeneratorAuditListener']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='sort']]/SLIST/LITERAL_FOR/SLIST"
                + "/LITERAL_FOR\"/>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event1, event2);
    }

    @Test
    public void testOnlyOneMatching() {
        final AuditEvent event1 = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                10, 5, MethodParamPadCheck.class);

        final AuditEvent event2 = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                5, 5, JavadocVariableCheck.class);

        final AuditEvent event3 = createAuditEvent("InputXpathFileGeneratorAuditListener.java",
                FOURTH_MESSAGE);

        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputXpathFileGeneratorAuditListener.java\"" + EOL
                + "       id=\"JavadocModuleId\"" + EOL
                + "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathFileGeneratorAuditListener']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='isValid']]\"/>" + EOL
                + "</suppressions>" + EOL;

        verifyOutput(expected, event1, event2, event3);
    }

    @Test
    public void testCloseStream() {
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(outStream, OutputStreamOptions.CLOSE);
        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(1);
    }

    @Test
    public void testNoCloseStream() {
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(outStream, OutputStreamOptions.NONE);
        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(0);
    }

    @Test
    public void testNoCloseStreamTwo() {
        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(outStream, null);
        listener.finishLocalSetup();
        listener.auditStarted(null);
        listener.auditFinished(null);

        assertWithMessage("Invalid close count")
            .that(outStream.getCloseCount())
            .isEqualTo(0);
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

    private static Violation createViolation(int lineNumber,
                                                                   int columnNumber, int tokenType,
                                                                   String moduleId,
                                                                   Class<?> sourceClass) {
        return new Violation(lineNumber, columnNumber, tokenType,
                "messages.properties", null, null,
                SeverityLevel.ERROR, moduleId, sourceClass, null);
    }

    private static TreeWalkerAuditEvent createTreeWalkerAuditEvent(String fileName,
                                                                   Violation violation)
            throws Exception {
        final File file = new File(getPath(fileName));
        final FileText fileText = new FileText(
                file.getAbsoluteFile(),
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        final FileContents fileContents = new FileContents(fileText);
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);

        return new TreeWalkerAuditEvent(fileContents, fileName,
                violation, rootAst);
    }

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/xpathfilegeneratorauditlistener/"
                + filename;
    }

    private static void verifyOutput(String expected, AuditEvent... events) {
        final TestByteArrayOutputStream out = new TestByteArrayOutputStream();

        final XpathFileGeneratorAuditListener listener =
                new XpathFileGeneratorAuditListener(out, OutputStreamOptions.CLOSE);

        for (AuditEvent event : events) {
            listener.addError(event);
        }

        listener.auditFinished(null);

        assertWithMessage("Output stream flush count")
                .that(out.flushCount)
                .isEqualTo(TestUtil.adjustFlushCountForOutputStreamClose(1));
        assertWithMessage("Output stream close count")
                .that(out.closeCount)
                .isEqualTo(1);

        final String actual = out.toString();
        assertWithMessage("Invalid suppressions file content")
            .that(actual)
            .isEqualTo(expected);
    }

    private static final class TestByteArrayOutputStream extends ByteArrayOutputStream {

        private int closeCount;
        private int flushCount;

        @Override
        public void close() {
            closeCount++;
        }

        @Override
        public void flush() {
            flushCount++;
        }

    }
}
