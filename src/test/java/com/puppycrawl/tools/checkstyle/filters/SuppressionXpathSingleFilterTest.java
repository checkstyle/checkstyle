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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressionXpathSingleFilterTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathsinglefilter";
    }

    @Test
    public void testMatching() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterMatchingTokenType.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingTokenType() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingTokenType.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingLineNumber() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "21:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingLineNumber.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingColumnNumber() throws Exception {
        final String[] expected = {
            "23:11: " + getCheckMessage(TypeNameCheck.class, MSG_INVALID_PATTERN,
                                        "testClass", "^[A-Z][a-zA-Z0-9]*$"),
            "26:11: " + getCheckMessage(TypeNameCheck.class, MSG_INVALID_PATTERN,
                                        "anotherTestClass", "^[A-Z][a-zA-Z0-9]*$"),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingColumnNumber.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testComplexQuery() throws Exception {
        final String[] expected = {
            "27:21: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "3.14"),
            "28:16: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "123"),
            "32:28: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "123"),
        };
        final String[] suppressed = {
            "27:21: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "3.14"),
        };
        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterComplexQuery.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testIncorrectQuery() {
        final String xpath = "1@#";
        try {
            final Object test = createSuppressionXpathSingleFilter(
                    "InputSuppressionXpathSingleFilterComplexQuery", "Test",
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
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNoQuery.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNullFileName() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNullFileName.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingFileRegexp() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingFileRegexp.java"), expected,
            removeSuppressed(expected, suppressed));
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
    public void testNullViolation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNullViolation.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingModuleId() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingModuleId.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testMatchingModuleId() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterMatchingModuleId.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingChecks() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingCheck.java"), expected,
            removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNonMatchingFileNameModuleIdAndCheck() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNonMatchingFileNameModuleIdAndCheck.java"),
            expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testNullModuleIdAndNonMatchingChecks() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterNullModuleIdAndNonMatchingCheck.java"),
            expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testDecideByMessage() throws Exception {
        final String[] expected = {
            "28:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
            "31:21: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "3.14"),
            "32:16: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "123"),
            "36:28: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "123"),
        };

        final String[] suppressed = {
            "28:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressionXpathSingleFilterDecideByMessage.java"),
            expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testThrowException() throws Exception {
        final String xpath = "//CLASS_DEF[@text='InputSuppressionXpathSingleFilterComplexQuery']";
        final SuppressionXpathSingleFilter filter =
                createSuppressionXpathSingleFilter("InputSuppressionXpathSingleFilterComplexQuery",
                        "Test", null, null, xpath);
        final Violation message =
                new Violation(3, 0, TokenTypes.CLASS_DEF, "",
                        "", null, null, "id19",
                        getClass(), null);
        final FileContents fileContents = new FileContents(new FileText(
            new File(getPath("InputSuppressionXpathSingleFilterComplexQuery.java")),
            StandardCharsets.UTF_8.name()));
        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents,
                "InputSuppressionXpathSingleFilterComplexQuery.java", message, null);
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

    @Test
    public void testAllNullConfiguration() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressionXpathSingleFilterAllNullConfiguration.java"),
                expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testDecideByIdAndExpression() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressionXpathSingleFilterDecideByIdAndExpression.java"),
                expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testDefaultFileProperty() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "20:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressionXpathSingleFilterDefaultFileProperty.java"),
                expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testDecideByCheck() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "18:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressionXpathSingleFilterDecideByCheck.java"),
                expected, removeSuppressed(expected, suppressed));
    }

    @Test
    public void testDecideById() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        final String[] suppressed = {
            "19:1: " + getCheckMessage(MissingJavadocTypeCheck.class, MSG_JAVADOC_MISSING),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressionXpathSingleFilterDecideById.java"),
                expected, removeSuppressed(expected, suppressed));
    }

    /**
     * This test is required to cover pitest mutation
     * for reset of 'file' field in SuppressionXpathSingleFilter.
     * This not possible to reproduce by natural execution of Checkstyle
     * as config is never changed in runtime.
     * Projects that use us by api can reproduce this case.
     * We need to allow users to reset module property to default state.
     *
     * @throws Exception when there is problem to load Input file
     */
    @Test
    public void testUpdateFilterFileSettingInRunTime() throws Exception {
        final File file = new File(getPath("InputSuppressionXpathSingleFilterComplexQuery.java"));

        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        filter.setChecks("MagicNumber");
        filter.finishLocalSetup();

        final Violation violation = new Violation(27, 21, TokenTypes.NUM_DOUBLE, "",
                        "", null, null, null,
                        MagicNumberCheck.class, null);

        final FileContents fileContents =
                new FileContents(new FileText(file, StandardCharsets.UTF_8.name()));

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                violation, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));

        assertWithMessage("match ia expected as 'files' is defaulted")
                .that(filter.accept(ev))
                .isFalse();

        // set non-default value for files
        filter.setFiles(Pattern.quote(file.getPath() + ".never.match"));
        filter.finishLocalSetup();

        assertWithMessage("no match is expected due to weird value in 'files'")
                .that(filter.accept(ev))
                .isTrue();

        // reset files to default value of filter
        filter.setFiles(null);
        filter.finishLocalSetup();

        assertWithMessage("match is expected as 'files' is defaulted")
                .that(filter.accept(ev))
                .isFalse();
    }

    /**
     * This test is required to cover pitest mutation
     * for reset of 'checks' field in SuppressionXpathSingleFilter.
     * This not possible to reproduce by natural execution of Checkstyle
     * as config is never changed in runtime.
     * Projects that use us by api can reproduce this case.
     * We need to allow users to reset module property to default state.
     *
     * @throws Exception when there is problem to load Input file
     */
    @Test
    public void testUpdateFilterChecksSettingInRunTime() throws Exception {
        final File file = new File(getPath("InputSuppressionXpathSingleFilterComplexQuery.java"));

        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        filter.setChecks("MagicNumber");
        filter.finishLocalSetup();

        final Violation violation = new Violation(27, 21, TokenTypes.NUM_DOUBLE, "",
                "", null, null, null,
                MagicNumberCheck.class, null);

        final FileContents fileContents =
                new FileContents(new FileText(file, StandardCharsets.UTF_8.name()));

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                violation, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));

        assertWithMessage("match is expected as 'checks' is set")
                .that(filter.accept(ev))
                .isFalse();

        // reset checks to default value of filter
        filter.setChecks(null);
        filter.finishLocalSetup();

        assertWithMessage("no match is expected as whole filter is defaulted (empty)")
                .that(filter.accept(ev))
                .isTrue();
    }

    /**
     * This test is required to cover pitest mutation
     * for reset of 'message' field in SuppressionXpathSingleFilter.
     * This not possible to reproduce by natural execution of Checkstyle
     * as config is never changed in runtime.
     * Projects that use us by api can reproduce this case.
     * We need to allow users to reset module property to default state.
     *
     * @throws Exception when there is problem to load Input file
     */

    @Test
    public void testSetMessageHandlesNullCorrectly() throws Exception {
        final File file = new File(getPath("InputSuppressionXpathSingleFilterComplexQuery.java"));

        final SuppressionXpathSingleFilter filter = new SuppressionXpathSingleFilter();
        filter.setMessage("MagicNumber");
        filter.finishLocalSetup();

        final Violation violation = new Violation(27, 21, TokenTypes.NUM_DOUBLE, "",
                "", null, null, null,
                MagicNumberCheck.class, "MagicNumber");

        final FileContents fileContents =
                new FileContents(new FileText(file, StandardCharsets.UTF_8.name()));

        final TreeWalkerAuditEvent ev = new TreeWalkerAuditEvent(fileContents, file.getName(),
                violation, JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS));

        assertWithMessage("match is expected as 'message' is set")
                .that(filter.accept(ev))
                .isFalse();

        filter.setMessage(null);
        filter.finishLocalSetup();

        assertWithMessage("no match is expected as whole filter is defaulted (empty)")
                .that(filter.accept(ev))
                .isTrue();
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

}
