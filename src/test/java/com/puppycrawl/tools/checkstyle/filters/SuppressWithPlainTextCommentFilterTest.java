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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_FILE_CONTAINS_TAB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithPlainTextCommentFilterTest extends AbstractModuleTestSupport {

    private static final String MSG_REGEXP_EXCEEDED = "regexp.exceeded";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    public void testFilterWithDefaultConfig() throws Exception {
        final String[] suppressed = {
            "20:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "28:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "20:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "24:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "28:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterWithDefaultCfg.java"),
            violationMessages, removeSuppressed(violationMessages, suppressed));
    }

    @Test
    public void testChangeOffAndOnFormat() throws Exception {
        final String[] suppressed = {
            "20:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "27:30: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessage = {
            "20:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "24:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "27:30: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "30:13: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java"),
            violationMessage, removeSuppressed(violationMessage, suppressed));
    }

    @Test
    public void testSuppressionCommentsInXmlFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("offCommentFormat", "CS-OFF");
        filterCfg.addProperty("onCommentFormat", "CS-ON");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addProperty("eachLine", "true");

        final String[] suppressed = {
            "7:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "7:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.xml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInPropertiesFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("offCommentFormat", "# CHECKSTYLE:OFF");
        filterCfg.addProperty("onCommentFormat", "# CHECKSTYLE:ON");

        final DefaultConfiguration checkCfg = createModuleConfig(RegexpSinglelineCheck.class);
        checkCfg.addProperty("format", "^key[0-9]=$");

        final String[] suppressed = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
        };

        final String[] violationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
            "4: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^key[0-9]=$"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.properties",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInSqlFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("offCommentFormat", "-- CHECKSTYLE OFF");
        filterCfg.addProperty("onCommentFormat", "-- CHECKSTYLE ON");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addProperty("eachLine", "true");

        final String[] suppressed = {
            "2:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "2:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "5:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilter.sql",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, checkCfg
        );
    }

    @Test
    public void testSuppressionCommentsInJavaScriptFile() throws Exception {
        final String[] suppressed = {
            "22: " + getCheckMessage(RegexpSinglelineCheck.class,
                    MSG_REGEXP_EXCEEDED, ".*\\s===.*"),
        };

        final String[] violationMessages = {
            "22: " + getCheckMessage(RegexpSinglelineCheck.class,
                    MSG_REGEXP_EXCEEDED, ".*\\s===.*"),
            "25: " + getCheckMessage(RegexpSinglelineCheck.class,
                    MSG_REGEXP_EXCEEDED, ".*\\s===.*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilter.js"),
            violationMessages,
            removeSuppressed(violationMessages, suppressed)
        );
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("checkFormat", "e[l");
        filterCfg.addProperty("onCommentFormat", "// cs-on");
        filterCfg.addProperty("offCommentFormat", "// cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addProperty("eachLine", "true");

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] violationMessages = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, checkCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    public void testInvalidIdFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("idFormat", "e[l");
        filterCfg.addProperty("onCommentFormat", "// cs-on");
        filterCfg.addProperty("offCommentFormat", "// cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addProperty("eachLine", "true");

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
                CommonUtil.EMPTY_STRING_ARRAY, filterCfg, checkCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    public void testInvalidMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("messageFormat", "e[l");
        filterCfg.addProperty("onCommentFormat", "// cs-on");
        filterCfg.addProperty("offCommentFormat", "// cs-off");

        final DefaultConfiguration checkCfg = createModuleConfig(FileTabCharacterCheck.class);
        checkCfg.addProperty("eachLine", "true");

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] violationMessages = {
            "5:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_FILE_CONTAINS_TAB),
            "8:7: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "10:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments.java",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, checkCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    public void testInvalidMessageFormatInSqlFile() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("onCommentFormat", "CSON (\\w+)");
        filterCfg.addProperty("messageFormat", "e[l");

        final DefaultConfiguration checkCfg = createModuleConfig(RegexpSinglelineCheck.class);
        checkCfg.addProperty("format", "^.*COUNT\\(\\*\\).*$");

        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] violationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                    "^.*COUNT\\(\\*\\).*$"),
        };

        try {
            verifySuppressed(
                "InputSuppressWithPlainTextCommentFilterWithCustomOnComment.sql",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, checkCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    public void testAcceptNullViolation() {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final AuditEvent auditEvent = new AuditEvent(this);
        assertWithMessage("Filter should accept audit event")
                .that(filter.accept(auditEvent))
                .isTrue();
        assertWithMessage("File name should not be null")
            .that(auditEvent.getFileName())
            .isNull();
    }

    /**
     * Our goal is 100% test coverage, for this we use white-box testing.
     * So we need access to the implementation details. For this reason, it is necessary
     * to use reflection to gain access to the inner type {@code Suppression} here.
     */
    @Test
    public void testEqualsAndHashCodeOfSuppressionClass() throws ClassNotFoundException {
        final Class<?> suppressionClass = TestUtil.getInnerClassType(
                SuppressWithPlainTextCommentFilter.class, "Suppression");
        final EqualsVerifierReport ev = EqualsVerifier
                .forClass(suppressionClass).usingGetClass()
                .report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testSuppressByCheck() throws Exception {
        final String[] suppressedViolationMessages = {
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] expectedViolationMessages = {
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "41: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
                getPath("InputSuppressWithPlainTextCommentFilterSuppressById.java"),
                expectedViolationMessages,
                removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testSuppressByModuleId() throws Exception {
        final String[] suppressedViolationMessages = {
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        final String[] expectedViolationMessages = {
            "30: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "41: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterSuppressById2.java"),
            expectedViolationMessages,
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testSuppressByCheckAndModuleId() throws Exception {
        final String[] suppressedViolationMessages = {
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] expectedViolationMessages = {
            "30: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "41: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterSuppressById3.java"),
            expectedViolationMessages,
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testSuppressByCheckAndNonMatchingModuleId() throws Exception {
        final String[] suppressedViolationMessages = CommonUtil.EMPTY_STRING_ARRAY;

        final String[] expectedViolationMessages = {
            "30: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "41: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterSuppressById4.java"),
            expectedViolationMessages,
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testSuppressByModuleIdWithNullModuleId() throws Exception {
        final String[] suppressedViolationMessages = {
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        final String[] expectedViolationMessages = {
            "30: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "33: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "38: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "41: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterSuppressById5.java"),
            expectedViolationMessages,
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testSuppressedByIdJavadocCheck() throws Exception {
        final String[] suppressedViolationMessages = {
            "28: " + getCheckMessage(JavadocMethodCheck.class, MSG_RETURN_EXPECTED),
            "32:9: " + getCheckMessage(JavadocMethodCheck.class,
                                       MSG_UNUSED_TAG, "@param", "unused"),
            "39:22: " + getCheckMessage(JavadocMethodCheck.class,
                                        MSG_EXPECTED_TAG, "@param", "a"),
        };

        final String[] expectedViolationMessages = {
            "28: " + getCheckMessage(JavadocMethodCheck.class, MSG_RETURN_EXPECTED),
            "32:9: " + getCheckMessage(JavadocMethodCheck.class,
                                       MSG_UNUSED_TAG, "@param", "unused"),
            "39:22: " + getCheckMessage(JavadocMethodCheck.class,
                                        MSG_EXPECTED_TAG, "@param", "a"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterSuppressByIdJavadocCheck.java"),
            expectedViolationMessages,
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages)
        );
    }

    @Test
    public void testAcceptThrowsIllegalStateExceptionAsFileNotFound() {
        final Violation message = new Violation(1, 1, 1, TokenTypes.CLASS_DEF,
            "messages.properties", "key", null, SeverityLevel.ERROR, null, getClass(), null);
        final String fileName = "nonexisting_file";
        final AuditEvent auditEvent = new AuditEvent(this, fileName, message);

        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();

        try {
            filter.accept(auditEvent);
            assertWithMessage(IllegalStateException.class.getSimpleName() + " is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Cannot read source file: " + fileName);

            final Throwable cause = ex.getCause();
            assertWithMessage("Exception cause has invalid type")
                    .that(cause)
                    .isInstanceOf(FileNotFoundException.class);
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo(fileName + " (No such file or directory)");
        }
    }

    @Test
    public void testFilterWithCustomMessageFormat() throws Exception {
        final String[] suppressed = {
            "34:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
        };

        final String[] violationMessages = {
            "32: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "34:1: " + getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB),
            "34: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "36: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
            "39: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                ".*[a-zA-Z][0-9].*"),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithPlainTextCommentFilterCustomMessageFormat.java"),
            violationMessages, removeSuppressed(violationMessages, suppressed)
        );
    }

    @Test
    public void testFilterWithIdAndCustomMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("offCommentFormat", "CHECKSTYLE stop (\\w+) (\\w+)");
        filterCfg.addProperty("onCommentFormat", "CHECKSTYLE resume (\\w+) (\\w+)");
        filterCfg.addProperty("idFormat", "$1");
        filterCfg.addProperty("messageFormat", "$2");

        final DefaultConfiguration regexpCheckCfg = createModuleConfig(RegexpSinglelineCheck.class);
        regexpCheckCfg.addProperty("id", "warning");
        regexpCheckCfg.addProperty("format", "^.*COUNT\\(\\*\\).*$");

        final String[] suppressedViolationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
        };

        final String[] expectedViolationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
            "5: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
            "8: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterCustomMessageFormat.sql",
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages),
            filterCfg, regexpCheckCfg
        );
    }

    @Test
    public void testFilterWithCheckAndCustomMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithPlainTextCommentFilter.class);
        filterCfg.addProperty("offCommentFormat", "CHECKSTYLE stop (\\w+) (\\w+)");
        filterCfg.addProperty("onCommentFormat", "CHECKSTYLE resume (\\w+) (\\w+)");
        filterCfg.addProperty("checkFormat", "RegexpSinglelineCheck");
        filterCfg.addProperty("messageFormat", "$2");

        final DefaultConfiguration regexpCheckCfg = createModuleConfig(RegexpSinglelineCheck.class);
        regexpCheckCfg.addProperty("id", "warning");
        regexpCheckCfg.addProperty("format", "^.*COUNT\\(\\*\\).*$");

        final String[] suppressedViolationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
        };

        final String[] expectedViolationMessages = {
            "2: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
            "5: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
            "8: " + getCheckMessage(RegexpSinglelineCheck.class, MSG_REGEXP_EXCEEDED,
                "^.*COUNT\\(\\*\\).*$"),
        };

        verifySuppressed(
            "InputSuppressWithPlainTextCommentFilterCustomMessageFormat.sql",
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages),
            filterCfg, regexpCheckCfg
        );
    }

    @Test
    public void testFilterWithDirectory() throws IOException {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final AuditEvent event = new AuditEvent(this, getPath(""), new Violation(1, 1,
                "bundle", "key", null, SeverityLevel.ERROR, "moduleId", getClass(),
                "customMessage"));

        assertWithMessage("filter should accept directory")
                .that(filter.accept(event))
                .isTrue();
    }

    private void verifySuppressed(String fileNameWithExtension, String[] violationMessages,
                                  Configuration... childConfigs) throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);

        Arrays.stream(childConfigs).forEach(checkerConfig::addChild);

        final String fileExtension = CommonUtil.getFileExtension(fileNameWithExtension);
        checkerConfig.addProperty("fileExtensions", fileExtension);

        verify(checkerConfig, getPath(fileNameWithExtension), violationMessages);
    }

}
