////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class SuppressionXpathSingleFilterTest
        extends AbstractModuleTestSupport {

    private File file;
    private FileContents fileContents;

    @BeforeEach
    public void setUp() throws Exception {
        file = new File(getPath("InputSuppressionXpathSingleFilter.java"));
        fileContents = new FileContents(new FileText(file,
                StandardCharsets.UTF_8.name()));
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathsinglefilter";
    }

    @Test
    public void testMatching() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT"
                + "[@text='InputSuppressionXpathSingleFilter']]";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be rejected")
                .that(filter.accept(ev))
                .isFalse();
    }

    @Test
    public void testNonMatchingTokenType() throws Exception {
        final String xpath = "//METHOD_DEF[@text='countTokens']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final String xpath = "//CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(100, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final String xpath = "//CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 100,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testComplexQuery() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='pi'] and "
                + "../../IDENT[@text='countTokens']] "
                + "| //VARIABLE_DEF[./IDENT[@text='someVariable'] and ../../IDENT[@text='sum']]";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent eventOne = createEvent(5, 8,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventTwo = createEvent(10, 4,
                TokenTypes.VARIABLE_DEF);
        final TreeWalkerAuditEvent eventThree = createEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        assertWithMessage("Event should be rejected")
            .that(filter.accept(eventOne))
            .isFalse();
        assertWithMessage("Event should be accepted")
            .that(filter.accept(eventTwo))
            .isTrue();
        assertWithMessage("Event should be rejected")
            .that(filter.accept(eventThree))
            .isFalse();
    }

    @Test
    public void testIncorrectQuery() {
        final String xpath = "1@#";
        try {
            final Object test = createSuppressionXpathSingleFilter(
                    "InputSuppressionXpathSingleFilter", "Test",
                    null, null, xpath);
            assertWithMessage("Exception was expected but got " + test).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Message should be: Unexpected xpath query")
                    .that(ex.getMessage())
                    .contains("Incorrect xpath query");
        }
    }

    @Test
    public void testNoQuery() throws Exception {
        final TreeWalkerAuditEvent event = createEvent(15, 8,
                TokenTypes.VARIABLE_DEF);
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, null);
        assertWithMessage("Event should be accepted")
                .that(filter.accept(event))
                .isFalse();
    }

    @Test
    public void testNullFileName() {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                null, null, null);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("NonMatchingRegexp", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testInvalidFileRegexp() {
        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        try {
            filter.setFiles("e[l");
            assertWithMessage("PatternSyntaxException is expected").fail();
        }
        catch (PatternSyntaxException ex) {
            assertWithMessage("Message should be: Unclosed character class")
                    .that(ex.getMessage())
                    .contains("Unclosed character class");
        }
    }

    @Test
    public void testInvalidCheckRegexp() {
        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        try {
            filter.setChecks("e[l");
            assertWithMessage("PatternSyntaxException is expected").fail();
        }
        catch (PatternSyntaxException ex) {
            assertWithMessage("Message should be: Unclosed character class")
                    .that(ex.getMessage())
                    .contains("Unclosed character class");
        }
    }

    @Test
    public void testNullViolation() {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, null, xpath);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(null,
                file.getName(), null, null);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, "id19", xpath);
        final Violation message =
                new Violation(3, 0, TokenTypes.CLASS_DEF, "", "",
                        null, null, "id20",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputSuppressionXpathSingleFilter']]";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter", "Test",
                        null, "id19", xpath);
        final Violation message =
                new Violation(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertWithMessage("Event should be rejected")
                .that(filter.accept(ev))
                .isFalse();
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter = createSuppressionXpathSingleFilter(
                "InputSuppressionXpathSingleFilter", "NonMatchingRegexp",
                null, "id19", xpath);
        final Violation message =
                new Violation(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNonMatchingFileNameModuleIdAndCheck() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter",
                        null, null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testNullModuleIdAndNonMatchingChecks() throws Exception {
        final String xpath = "NON_MATCHING_QUERY";
        final SuppressionXpathSingleFilter filter = createSuppressionXpathSingleFilter(
                "InputSuppressionXpathSingleFilter", "NonMatchingRegexp", null, null, xpath);
        final TreeWalkerAuditEvent ev = createEvent(3, 0,
                TokenTypes.CLASS_DEF);
        assertWithMessage("Event should be accepted")
            .that(filter.accept(ev)).isTrue();
    }

    @Test
    public void testDecideByMessage() throws Exception {
        final Violation message = new Violation(0, 0,
                TokenTypes.CLASS_DEF, "", "",
                null, null, null, getClass(), "Test");
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                message, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
        final SuppressionXpathSingleFilter filter1 = createSuppressionXpathSingleFilter(
                null, null, "Test", null, null);
        final SuppressionXpathSingleFilter filter2 = createSuppressionXpathSingleFilter(
                null, null, "Bad", null, null);
        assertWithMessage("Message match")
            .that(filter1.accept(ev))
            .isFalse();
        assertWithMessage("Message not match")
            .that(filter2.accept(ev))
            .isTrue();
    }

    @Test
    public void testThrowException() {
        final String xpath = "//CLASS_DEF[@text='InputSuppressionXpathSingleFilter']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilter",
                        "Test", null, null, xpath);
        final Violation message =
                new Violation(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents,
                file.getName(), message, null);
        try {
            filter.accept(ev);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Exception message does not match expected one")
                    .that(ex.getMessage())
                    .contains("Cannot initialize context and evaluate query");
        }
    }

    private static SuppressionXpathSingleFilter createSuppressionXpathSingleFilter(
            String files, String checks, String message, String moduleId, String query) {
        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        filter.setFiles(files);
        filter.setChecks(checks);
        filter.setMessage(message);
        filter.setId(moduleId);
        filter.setQuery(query);
        filter.finishLocalSetup();
        return filter;
    }

    private TreeWalkerAuditEvent createEvent(int line, int column, int tokenType)
            throws Exception {
        final Violation message =
                new Violation(line, column, tokenType, "", "", null, null, null,
                        getClass(), null);
        return new TreeWalkerAuditEvent(fileContents, file.getName(), message,
                JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));
    }

}
