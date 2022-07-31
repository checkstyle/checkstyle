///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;

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
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithNearbyPlainTextCommentFilterTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbyplaintextcommentfilter";
    }

    @Test
    public void testDefaultConfig() throws Exception {
        final int expectedLineLength = 90;

        final String[] violationMessages = {
            "24:1: " + getFileTabCharacterCheckMessage(),
            "27:1: " + getFileTabCharacterCheckMessage(),
            "31:1: " + getFileTabCharacterCheckMessage(),
            "36: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "38: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "41:1: " + getFileTabCharacterCheckMessage(),
            "45:1: " + getFileTabCharacterCheckMessage(),
            "52: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "55:1: " + getFileTabCharacterCheckMessage(),
            "55: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "61:1: " + getFileTabCharacterCheckMessage(),
        };

        final String[] suppressedMessages = {
            "31:1: " + getFileTabCharacterCheckMessage(),
            "38: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "45:1: " + getFileTabCharacterCheckMessage(),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "61:1: " + getFileTabCharacterCheckMessage(),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyPlainTextCommentFilterDefaultConfig.java"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages));
    }

    @Test
    public void testCommentFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("commentFormat", "cs (\\w+)");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 90;
        lineLengthCheckCfg.addProperty("max", String.valueOf(expectedLineLength));

        final String[] violationMessages = {
            "26: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "28:1: " + getFileTabCharacterCheckMessage(),
            "40:1: " + getFileTabCharacterCheckMessage(),
            "45:1: " + getFileTabCharacterCheckMessage(),
            "57: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "61:1: " + getFileTabCharacterCheckMessage(),
            "74:1: " + getFileTabCharacterCheckMessage(),
            "86: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "91:1: " + getFileTabCharacterCheckMessage(),
            "103:1: " + getFileTabCharacterCheckMessage(),
            "108: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        final String[] suppressed = {
            "28:1: " + getFileTabCharacterCheckMessage(),
            "40:1: " + getFileTabCharacterCheckMessage(),
            "45:1: " + getFileTabCharacterCheckMessage(),
            "57: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "61:1: " + getFileTabCharacterCheckMessage(),
            "74:1: " + getFileTabCharacterCheckMessage(),
            "86: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "103:1: " + getFileTabCharacterCheckMessage(),
            "108: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterCommentFormat.css",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, tabCharacterCheckCfg, lineLengthCheckCfg
        );
    }

    @Test
    public void testCheckFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("checkFormat", ".*Line.*");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "30: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 87),
            "45:1: " + getFileTabCharacterCheckMessage(),
            "51:1: " + getFileTabCharacterCheckMessage(),
        };

        final String[] suppressed = {
            "30: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 87),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterCheckFormat.sh",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, tabCharacterCheckCfg, lineLengthCheckCfg
        );
    }

    @Test
    public void testMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
                createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("messageFormat", ".*tab.*");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 90;
        lineLengthCheckCfg.addProperty("max", String.valueOf(expectedLineLength));

        final String[] violationMessages = {
            "28:1: " + getFileTabCharacterCheckMessage(),
            "30:1: " + getFileTabCharacterCheckMessage(),
            "43:1: " + getFileTabCharacterCheckMessage(),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 96),
            "44: " + getLineLengthCheckMessage(expectedLineLength, 97),
        };

        final String[] suppressed = {
            "28:1: " + getFileTabCharacterCheckMessage(),
            "30:1: " + getFileTabCharacterCheckMessage(),
            "43:1: " + getFileTabCharacterCheckMessage(),
        };

        verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterMessageFormat.xml",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, tabCharacterCheckCfg, lineLengthCheckCfg
        );
    }

    @Test
    public void testIdFormat() throws Exception {
        final DefaultConfiguration filterCfg =
                createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("idFormat", "ignoreMe");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");
        tabCharacterCheckCfg.addProperty("id", "ignoreMe");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "26:1: " + getFileTabCharacterCheckMessage(),
            "28:1: " + getFileTabCharacterCheckMessage(),
            "32: " + getLineLengthCheckMessage(expectedLineLength, 83),
            "35:1: " + getFileTabCharacterCheckMessage(),
            "38:1: " + getFileTabCharacterCheckMessage(),
            "40: " + getLineLengthCheckMessage(expectedLineLength, 84),
        };

        final String[] suppressed = {
            "26:1: " + getFileTabCharacterCheckMessage(),
            "28:1: " + getFileTabCharacterCheckMessage(),
            "35:1: " + getFileTabCharacterCheckMessage(),
            "38:1: " + getFileTabCharacterCheckMessage(),
        };

        verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterIdFormat.html",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, tabCharacterCheckCfg, lineLengthCheckCfg
        );
    }

    @Test
    public void testInfluenceFormatPositive3() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("influenceFormat", "3");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "22: " + getLineLengthCheckMessage(expectedLineLength, 82),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "32:1: " + getFileTabCharacterCheckMessage(),
            "33:1: " + getFileTabCharacterCheckMessage(),
            "34:1: " + getFileTabCharacterCheckMessage(),
            "35:1: " + getFileTabCharacterCheckMessage(),
            "36:1: " + getFileTabCharacterCheckMessage(),
            "38:1: " + getFileTabCharacterCheckMessage(),
            "40:1: " + getFileTabCharacterCheckMessage(),
        };

        final String[] suppressed = {
            "25: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "32:1: " + getFileTabCharacterCheckMessage(),
            "33:1: " + getFileTabCharacterCheckMessage(),
            "34:1: " + getFileTabCharacterCheckMessage(),
            "38:1: " + getFileTabCharacterCheckMessage(),
            "40:1: " + getFileTabCharacterCheckMessage(),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatPositive3.sql",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, tabCharacterCheckCfg, lineLengthCheckCfg
        );
    }

    @Test
    public void testInfluenceFormatNegative2() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("influenceFormat", "-2");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final String[] violationMessages = {
            "19:3: " + getFileTabCharacterCheckMessage(),
            "20:5: " + getFileTabCharacterCheckMessage(),
            "38:1: " + getFileTabCharacterCheckMessage(),
            "39:7: " + getFileTabCharacterCheckMessage(),
            "40:9: " + getFileTabCharacterCheckMessage(),
            "42:5: " + getFileTabCharacterCheckMessage(),
        };

        final String[] suppressed = {
            "19:3: " + getFileTabCharacterCheckMessage(),
            "20:5: " + getFileTabCharacterCheckMessage(),
            "39:7: " + getFileTabCharacterCheckMessage(),
            "40:9: " + getFileTabCharacterCheckMessage(),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatNegative2.yml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, tabCharacterCheckCfg
        );
    }

    @Test
    public void testVariableCheckFormatAndInfluenceFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("commentFormat", "SUPPRESS CHECKSTYLE (\\w+) - ([+-]\\d+) Lines");
        filterCfg.addProperty("checkFormat", "$1");
        filterCfg.addProperty("influenceFormat", "$2");

        final DefaultConfiguration tabCharacterCheckCfg =
                createModuleConfig(FileTabCharacterCheck.class);
        tabCharacterCheckCfg.addProperty("eachLine", "true");

        final String[] violationMessages = {
            "24:1: " + getFileTabCharacterCheckMessage(),
            "25:1: " + getFileTabCharacterCheckMessage(),
            "26:1: " + getFileTabCharacterCheckMessage(),
            "27:1: " + getFileTabCharacterCheckMessage(),
            "28:1: " + getFileTabCharacterCheckMessage(),
            "31:1: " + getFileTabCharacterCheckMessage(),
            "32:1: " + getFileTabCharacterCheckMessage(),
            "33:1: " + getFileTabCharacterCheckMessage(),
            "34:1: " + getFileTabCharacterCheckMessage(),
        };

        final String[] suppressed = {
            "24:1: " + getFileTabCharacterCheckMessage(),
            "25:1: " + getFileTabCharacterCheckMessage(),
            "26:1: " + getFileTabCharacterCheckMessage(),
            "27:1: " + getFileTabCharacterCheckMessage(),
            "32:1: " + getFileTabCharacterCheckMessage(),
            "33:1: " + getFileTabCharacterCheckMessage(),
            "34:1: " + getFileTabCharacterCheckMessage(),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilter"
                    + "VariableCheckFormatAndInfluenceFormat.xml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, tabCharacterCheckCfg
        );
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("checkFormat", "a![b");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "22: " + getLineLengthCheckMessage(expectedLineLength, 82),
        };

        final String[] suppressed = {
            "25: " + getLineLengthCheckMessage(expectedLineLength, 153),
        };

        try {
            verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatPositive3.sql",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, lineLengthCheckCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment a![b");
        }
    }

    @Test
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("influenceFormat", "a!b");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "22: " + getLineLengthCheckMessage(expectedLineLength, 82),
        };

        final String[] suppressed = {
            "25: " + getLineLengthCheckMessage(expectedLineLength, 153),
        };

        try {
            verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatPositive3.sql",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, lineLengthCheckCfg
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("unable to parse influence"
                        + " from 'SUPPRESS CHECKSTYLE LineLengthCheck' using a!b");
        }
    }

    @Test
    public void testAcceptNullViolation() {
        final SuppressWithNearbyPlainTextCommentFilter filter =
                new SuppressWithNearbyPlainTextCommentFilter();
        final AuditEvent auditEvent = new AuditEvent(this);
        assertWithMessage("Filter should accept audit event")
                .that(filter.accept(auditEvent))
                .isTrue();
        assertWithMessage("File name should not be null")
            .that(auditEvent.getFileName())
            .isNull();
    }

    @Test
    public void testThrowsIllegalStateExceptionWhenFileNotFound() {
        final Violation message = new Violation(1, 1, 1, TokenTypes.CLASS_DEF,
            "messages.properties", "key", null, SeverityLevel.ERROR, null, getClass(), null);
        final String fileName = "nonexisting_file";
        final AuditEvent auditEvent = new AuditEvent(this, fileName, message);

        final SuppressWithNearbyPlainTextCommentFilter filter =
                new SuppressWithNearbyPlainTextCommentFilter();

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
    public void testFilterWithDirectory() throws IOException {
        final SuppressWithNearbyPlainTextCommentFilter filter =
                new SuppressWithNearbyPlainTextCommentFilter();
        final AuditEvent event = new AuditEvent(this, getPath(""), new Violation(1, 1,
                "bundle", "key", null, SeverityLevel.ERROR, "moduleId", getClass(),
                "customMessage"));

        assertWithMessage("filter should accept directory")
                .that(filter.accept(event))
                .isTrue();
    }

    @Test
    public void testEqualsAndHashCodeOfSuppressionClass() throws ClassNotFoundException {
        final Class<?> suppressionClass = TestUtil.getInnerClassType(
                SuppressWithNearbyPlainTextCommentFilter.class, "Suppression");
        final EqualsVerifierReport ev = EqualsVerifier
                .forClass(suppressionClass).usingGetClass()
                .report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    private static String getFileTabCharacterCheckMessage() {
        return getCheckMessage(FileTabCharacterCheck.class, MSG_CONTAINS_TAB);
    }

    private static String getLineLengthCheckMessage(int expectedLength, int actualLength) {
        return getCheckMessage(LineLengthCheck.class, MSG_KEY, expectedLength, actualLength);
    }

    private void verifySuppressed(String fileNameWithExtension,
                                  String[] violationMessages,
                                  Configuration... childConfigs) throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);

        Arrays.stream(childConfigs).forEach(checkerConfig::addChild);

        final String fileExtension = CommonUtil.getFileExtension(fileNameWithExtension);
        checkerConfig.addProperty("fileExtensions", fileExtension);

        verify(checkerConfig, getPath(fileNameWithExtension), violationMessages);
    }

}
