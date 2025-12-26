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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_FILE_CONTAINS_TAB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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

    @TempDir
    public File temporaryFolder;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    void filterWithDefaultConfig() throws Exception {
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
    void changeOffAndOnFormat() throws Exception {
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
    void suppressionCommentsInXmlFile() throws Exception {
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
    void suppressionCommentsInPropertiesFile() throws Exception {
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
    void suppressionCommentsInSqlFile() throws Exception {
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
    void suppressionCommentsInJavaScriptFile() throws Exception {
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
    void invalidCheckFormat() throws Exception {
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
        catch (CheckstyleException exc) {
            final IllegalArgumentException cause = (IllegalArgumentException) exc.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    void invalidIdFormat() throws Exception {
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
        catch (CheckstyleException exc) {
            final IllegalArgumentException cause = (IllegalArgumentException) exc.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    void invalidMessageFormat() throws Exception {
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
        catch (CheckstyleException exc) {
            final IllegalArgumentException cause = (IllegalArgumentException) exc.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    void invalidMessageFormatInSqlFile() throws Exception {
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
        catch (CheckstyleException exc) {
            final IllegalArgumentException cause = (IllegalArgumentException) exc.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment e[l");
        }
    }

    @Test
    void acceptNullViolation() {
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
    void equalsAndHashCodeOfSuppressionClass() throws Exception {
        final Class<?> suppressionClass = TestUtil.getInnerClassType(
                SuppressWithPlainTextCommentFilter.class, "Suppression");
        final EqualsVerifierReport ev = EqualsVerifier
                .forClass(suppressionClass).usingGetClass()
                .report();
        assertWithMessage("Error: %s", ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    /**
     * Calls the filter twice and removes input file in between to ensure that
     * the file readed once will not read twice.
     * We cannot use {@link AbstractModuleTestSupport#verifyFilterWithInlineConfigParser}
     * because to kill pitest survival we need to remove target file between
     * filter execution to accept violation
     */
    @Test
    void cachingExecution() throws Exception {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final String inputPath =
                getPath("InputSuppressWithPlainTextCommentFilterCustomMessageFormat.java");
        final File tempFile = new File(temporaryFolder,
                "InputSuppressWithPlainTextCommentFilterCustomMessageFormat.java");
        Files.copy(new File(inputPath).toPath(), tempFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        final AuditEvent auditEvent1 = new AuditEvent(
                tempFile.getPath(), tempFile.getPath(),
                new Violation(1, null, null, null, null,
                        Object.class, null)
        );
        filter.accept(auditEvent1);
        final boolean deleted = tempFile.delete();
        assertWithMessage("Temporary file should be deleted.")
                .that(deleted).isTrue();
        final AuditEvent auditEvent2 = new AuditEvent(
                tempFile.getPath(), tempFile.getPath(),
                new Violation(2, null, null, null, null,
                        Object.class, null)
        );
        filter.accept(auditEvent2);

        assertWithMessage("Cache should handle missing file.")
                .that(tempFile.exists()).isFalse();
    }

    /**
     * Calls the filter on two consecutive input files and asserts that the
     * 'currentFileSuppressions' internal field is cleared after each run.
     * Our goal is to kill pitest survival and we need access to the implementation details
     * so {@link AbstractModuleTestSupport#verifyFilterWithInlineConfigParser} is not used.
     *
     * @throws IOException if an error occurs while formatting the path to the input file.
     */
    @Test
    void suppressionsAreClearedEachRun() throws Exception {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final Violation violation = new Violation(1, null, null,
                null, null, Object.class, null);

        final String fileName1 = getPath(
                        "InputSuppressWithPlainTextCommentFilterCustomMessageFormat.java");
        final AuditEvent event1 = new AuditEvent("", fileName1, violation);
        filter.accept(event1);

        final List<?> suppressions1 = getSuppressionsAfterExecution(filter);
        assertWithMessage("Invalid suppressions size")
            .that(suppressions1)
            .hasSize(4);

        final String fileName2 = getPath(
                        "InputSuppressWithPlainTextCommentFilterWithDefaultCfg.java");
        final AuditEvent event2 = new AuditEvent("", fileName2, violation);
        filter.accept(event2);

        final List<?> suppressions2 = getSuppressionsAfterExecution(filter);
        assertWithMessage("Invalid suppressions size")
            .that(suppressions2)
            .hasSize(6);
    }

    @Test
    void suppressByCheck() throws Exception {
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
    void suppressByModuleId() throws Exception {
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
    void suppressByCheckAndModuleId() throws Exception {
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
    void suppressByCheckAndNonMatchingModuleId() throws Exception {
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
    void suppressByModuleIdWithNullModuleId() throws Exception {
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
    void suppressedByIdJavadocCheck() throws Exception {
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
    void acceptThrowsIllegalStateExceptionAsFileNotFound() {
        final Violation message = new Violation(1, 1, 1, TokenTypes.CLASS_DEF,
            "messages.properties", "key", null, SeverityLevel.ERROR, null, getClass(), null);
        final String fileName = "nonexisting_file";
        final AuditEvent auditEvent = new AuditEvent(this, fileName, message);

        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();

        try {
            filter.accept(auditEvent);
            assertWithMessage("%s is expected", IllegalStateException.class.getSimpleName()).fail();
        }
        catch (IllegalStateException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Cannot read source file: " + fileName);

            final Throwable cause = exc.getCause();
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
    void filterWithCustomMessageFormat() throws Exception {
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
    void filterWithIdAndCustomMessageFormat() throws Exception {
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
    void filterWithCheckAndCustomMessageFormat() throws Exception {
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
    void filterWithDirectory() throws Exception {
        final SuppressWithPlainTextCommentFilter filter = new SuppressWithPlainTextCommentFilter();
        final AuditEvent event = new AuditEvent(this, getPath(""), new Violation(1, 1,
                "bundle", "key", null, SeverityLevel.ERROR, "moduleId", getClass(),
                "customMessage"));

        assertWithMessage("filter should accept directory")
                .that(filter.accept(event))
                .isTrue();
    }

    private static List<?> getSuppressionsAfterExecution(
                            SuppressWithPlainTextCommentFilter filter) {
        return TestUtil.getInternalState(filter, "currentFileSuppressionCache", List.class);
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
