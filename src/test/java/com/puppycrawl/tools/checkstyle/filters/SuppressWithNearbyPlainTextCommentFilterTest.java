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
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

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
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithNearbyPlainTextCommentFilterTest
    extends AbstractModuleTestSupport {

    private static final String REGEXP_SINGLELINE_CHECK_FORMAT = "this should not appear";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbyplaintextcommentfilter";
    }

    @Test
    public void testDefaultConfig() throws Exception {
        final int expectedLineLength = 90;
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] violationMessages = {
            "29: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "31: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "44:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant", pattern),
            "47:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant1", pattern),
        };

        final String[] suppressedMessages = {
            "31: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "47:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant1", pattern),
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

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 90;
        lineLengthCheckCfg.addProperty("max", String.valueOf(expectedLineLength));

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "39: " + getRegexpSinglelineCheckMessage(),
            "53: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "69: " + getRegexpSinglelineCheckMessage(),
            "76: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "86: " + getRegexpSinglelineCheckMessage(),
            "91: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        final String[] suppressed = {
            "39: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "39: " + getRegexpSinglelineCheckMessage(),
            "53: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "76: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "86: " + getRegexpSinglelineCheckMessage(),
            "91: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterCommentFormat.css",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testCheckFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("checkFormat", ".*Line.*");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "16: " + getRegexpSinglelineCheckMessage(),
            "34: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "35: " + getRegexpSinglelineCheckMessage(),
            "46: " + getLineLengthCheckMessage(expectedLineLength, 87),
        };

        final String[] suppressed = {
            "34: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "46: " + getLineLengthCheckMessage(expectedLineLength, 87),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterCheckFormat.bash",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
                createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("messageFormat", ".*longer.*");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 90;
        lineLengthCheckCfg.addProperty("max", String.valueOf(expectedLineLength));

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "32: " + getRegexpSinglelineCheckMessage(),
            "42: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "46: " + getLineLengthCheckMessage(expectedLineLength, 96),
        };

        final String[] suppressed = {
            "42: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "46: " + getLineLengthCheckMessage(expectedLineLength, 96),
        };

        verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterMessageFormat.xml",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testIdFormat() throws Exception {
        final DefaultConfiguration filterCfg =
                createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("idFormat", "ignoreMe");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        lineLengthCheckCfg.addProperty("id", "ignoreMe");
        final int expectedLineLength = 80;

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "16: " + getRegexpSinglelineCheckMessage(),
            "32: " + getLineLengthCheckMessage(expectedLineLength, 83),
            "27: " + getRegexpSinglelineCheckMessage(),
            "36: " + getLineLengthCheckMessage(expectedLineLength, 84),
        };

        final String[] suppressed = {
            "32: " + getLineLengthCheckMessage(expectedLineLength, 83),
            "36: " + getLineLengthCheckMessage(expectedLineLength, 84),
        };

        verifySuppressed(
                "InputSuppressWithNearbyPlainTextCommentFilterIdFormat.html",
                removeSuppressed(violationMessages, suppressed),
                filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testInfluenceFormatPositive3() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("influenceFormat", "3");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 82),
            "29: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "31: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "32: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
            "37: " + getRegexpSinglelineCheckMessage(),
        };

        final String[] suppressed = {
            "29: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "31: " + getLineLengthCheckMessage(expectedLineLength, 99),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
            "37: " + getRegexpSinglelineCheckMessage(),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatPositive3.sql",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testInfluenceFormatNegative2() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("influenceFormat", "-2");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final DefaultConfiguration regexpSinglelineCheckCfg =
                createModuleConfig(RegexpSinglelineCheck.class);
        regexpSinglelineCheckCfg.addProperty("format", REGEXP_SINGLELINE_CHECK_FORMAT);

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "31: " + getRegexpSinglelineCheckMessage(),
            "32: " + getRegexpSinglelineCheckMessage(),
            "33: " + getRegexpSinglelineCheckMessage(),
            "34: " + getRegexpSinglelineCheckMessage(),
        };

        final String[] suppressed = {
            "26: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "32: " + getRegexpSinglelineCheckMessage(),
            "33: " + getRegexpSinglelineCheckMessage(),
            "34: " + getRegexpSinglelineCheckMessage(),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilterInfluenceFormatNegative2.yml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, lineLengthCheckCfg, regexpSinglelineCheckCfg
        );
    }

    @Test
    public void testVariableCheckFormatAndInfluenceFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("commentFormat", "SUPPRESS CHECKSTYLE (\\w+) - ([+-]\\d+) Lines");
        filterCfg.addProperty("checkFormat", "$1");
        filterCfg.addProperty("influenceFormat", "$2");

        final DefaultConfiguration lineLengthCheckCfg =
                createModuleConfig(LineLengthCheck.class);
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "19: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "20: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "21: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "22: " + getLineLengthCheckMessage(expectedLineLength, 86),
            "24: " + getLineLengthCheckMessage(expectedLineLength, 86),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        final String[] suppressed = {
            "19: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "20: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "21: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        verifySuppressed(
            "InputSuppressWithNearbyPlainTextCommentFilter"
                    + "VariableCheckFormatAndInfluenceFormat.xml",
            removeSuppressed(violationMessages, suppressed),
            filterCfg, lineLengthCheckCfg
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
    public void testInvalidIdFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("idFormat", "a![b");

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
    public void testInvalidMessageFormat() throws Exception {
        final DefaultConfiguration filterCfg =
            createModuleConfig(SuppressWithNearbyPlainTextCommentFilter.class);
        filterCfg.addProperty("messageFormat", "a![b");

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

    private static String getLineLengthCheckMessage(int expectedLength, int actualLength) {
        return getCheckMessage(LineLengthCheck.class, MSG_KEY, expectedLength, actualLength);
    }

    private static String getRegexpSinglelineCheckMessage() {
        final String msgRegexExceeded = "regexp.exceeded";
        return getCheckMessage(RegexpSinglelineCheck.class,
                msgRegexExceeded, REGEXP_SINGLELINE_CHECK_FORMAT);
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
