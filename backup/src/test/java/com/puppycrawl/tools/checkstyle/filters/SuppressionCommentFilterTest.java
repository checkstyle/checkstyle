///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressionCommentFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "45:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "48:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "51:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "54:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "55:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "59:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "M2", "^[a-z][a-zA-Z0-9]*$"),
        "60:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "64:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "P", "^[a-z][a-zA-Z0-9]*$"),
        "67:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "Q", "^[a-z][a-zA-Z0-9]*$"),
        "70:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "R", "^[a-z][a-zA-Z0-9]*$"),
        "71:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "75:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
        "96:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "103:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "109:11: "
            + getCheckMessage(IllegalCatchCheck.class,
                IllegalCatchCheck.MSG_KEY, "RuntimeException"),
        "110:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "118:31: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter";
    }

    @Test
    public void testNone() throws Exception {
        final String[] messages = {
            "35:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
            "38:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
            "44:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "M2", "^[a-z][a-zA-Z0-9]*$"),
            "50:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "P", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "Q", "^[a-z][a-zA-Z0-9]*$"),
            "60:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "R", "^[a-z][a-zA-Z0-9]*$"),
            "61:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "65:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
            "86:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "93:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "99:11: "
                + getCheckMessage(IllegalCatchCheck.class,
                    IllegalCatchCheck.MSG_KEY, "RuntimeException"),
            "100:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "108:31: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilter.java"),
                messages, suppressed);
    }

    // Suppress all checks between default comments
    @Test
    public void testDefault() throws Exception {
        final String[] suppressed = {
            "48:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "75:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
            "96:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "103:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "118:31: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter2.java", suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final String[] suppressed = {
            "75:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
            "96:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "103:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter3.java", suppressed);
    }

    @Test
    public void testCheckCpp() throws Exception {
        final String[] suppressed = {
            "48:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "118:31: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter4.java", suppressed);
    }

    // Suppress all checks between CS_OFF and CS_ON
    @Test
    public void testOffFormat() throws Exception {
        final String[] suppressed = {
            "64:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "P", "^[a-z][a-zA-Z0-9]*$"),
            "70:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "R", "^[a-z][a-zA-Z0-9]*$"),
            "71:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "74:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter5.java", suppressed);
    }

    // Test suppression of checks of only one type
    //  Suppress only ConstantNameCheck between CS_OFF and CS_ON
    @Test
    public void testOffFormatCheck() throws Exception {
        final String[] suppressed = {
            "71:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter6.java", suppressed);
    }

    @Test
    public void testArgumentSuppression() throws Exception {
        final String[] suppressed = {
            "110:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter7.java", suppressed);
    }

    @Test
    public void testExpansion() throws Exception {
        final String[] suppressed = {
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "55:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "60:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verifySuppressedWithParser("InputSuppressionCommentFilter8.java", suppressed);
    }

    @Test
    public void testMessage() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser("InputSuppressionCommentFilter9.java", suppressed);
    }

    private void verifySuppressedWithParser(String fileName, String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(getPath(fileName), ALL_MESSAGES,
                removeSuppressed(ALL_MESSAGES, suppressed));
    }

    private void verifySuppressedWithParser(String fileName, String[] messages,
                                            String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, messages,
                removeSuppressed(messages, suppressed));
    }

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        final Object tag = getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:OFF").get(0);
        final EqualsVerifierReport ev = EqualsVerifier.forClass(tag.getClass())
                .usingGetClass().report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testToStringOfTagClass() {
        final Object tag = getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:OFF").get(0);
        assertWithMessage("Invalid toString result")
            .that(tag.toString())
            .isEqualTo("Tag[text='CHECKSTYLE:OFF', line=1, column=0, type=OFF,"
                    + " tagCheckRegexp=.*, tagMessageRegexp=null, tagIdRegexp=null]");
    }

    @Test
    public void testToStringOfTagClassWithMessage() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        filter.setMessageFormat(".*");
        final Object tag =
                getTagsAfterExecution(filter, "filename", "//CHECKSTYLE:ON").get(0);
        assertWithMessage("Invalid toString result")
            .that(tag.toString())
            .isEqualTo("Tag[text='CHECKSTYLE:ON', line=1, column=0, type=ON,"
                + " tagCheckRegexp=.*, tagMessageRegexp=.*, tagIdRegexp=null]");
    }

    @Test
    public void testCompareToOfTagClass() {
        final List<Comparable<Object>> tags1 =
                getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:OFF", " //CHECKSTYLE:ON");
        final Comparable<Object> tag1 = tags1.get(0);
        final Comparable<Object> tag2 = tags1.get(1);

        final List<Comparable<Object>> tags2 =
                getTagsAfterExecutionOnDefaultFilter(" //CHECKSTYLE:OFF");
        final Comparable<Object> tag3 = tags2.get(0);

        final List<Comparable<Object>> tags3 =
                getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:ON");
        final Comparable<Object> tag4 = tags3.get(0);

        assertWithMessage("Invalid comparing result")
                .that(tag1.compareTo(tag2) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(tag2.compareTo(tag1) > 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(tag1.compareTo(tag3) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(tag3.compareTo(tag1) > 0)
                .isTrue();
        final int actual = tag1.compareTo(tag4);
        assertWithMessage("Invalid comparing result")
            .that(actual)
            .isEqualTo(0);
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration treeWalkerConfig =
            createModuleConfig(TreeWalker.class);
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addProperty("checkFormat", "e[l");
        final DefaultConfiguration checkConfig =
            createModuleConfig(ConstantNameCheck.class);
        treeWalkerConfig.addChild(filterConfig);
        treeWalkerConfig.addChild(checkConfig);

        try {
            execute(treeWalkerConfig, getPath("InputSuppressionCommentFilter10.java"));
            assertWithMessage("Exception is expected").fail();
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
        final DefaultConfiguration treeWalkerConfig =
            createModuleConfig(TreeWalker.class);
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addProperty("messageFormat", "e[l");
        final DefaultConfiguration checkConfig =
            createModuleConfig(ConstantNameCheck.class);
        treeWalkerConfig.addChild(filterConfig);
        treeWalkerConfig.addChild(checkConfig);

        try {
            execute(treeWalkerConfig, getPath("InputSuppressionCommentFilter11.java"));
            assertWithMessage("Exception is expected").fail();
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
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        final FileContents contents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("//CHECKSTYLE:OFF: ConstantNameCheck", "line2")));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent auditEvent =
                new TreeWalkerAuditEvent(contents, null, null, null);
        assertWithMessage("Filter should accept audit event")
            .that(filter.accept(auditEvent))
                .isTrue();
        assertWithMessage("File name should not be null")
            .that(auditEvent.getFileName())
            .isNull();
    }

    @Test
    public void testAcceptNullFileContents() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        final FileContents contents = null;
        final TreeWalkerAuditEvent auditEvent = new TreeWalkerAuditEvent(contents, null,
                new Violation(1, null, null, null, null, Object.class, null), null);
        assertWithMessage("Filter should accept audit event")
                .that(filter.accept(auditEvent))
                .isTrue();
    }

    @Test
    public void testSuppressByCheck() throws Exception {
        final String[] suppressedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "51:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolation, suppressedViolation);
    }

    @Test
    public void testSuppressById() throws Exception {
        final String[] suppressedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "51:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilterSuppressById2.java"),
                expectedViolation, suppressedViolation);
    }

    @Test
    public void testSuppressByCheckAndId() throws Exception {
        final String[] suppressedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "51:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilterSuppressById3.java"),
                expectedViolation, suppressedViolation);
    }

    @Test
    public void testSuppressByIdAndMessage() throws Exception {
        final String[] suppressedViolation = {
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "51:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilterSuppressById4.java"),
                expectedViolation, suppressedViolation);
    }

    @Test
    public void testSuppressByCheckAndMessage() throws Exception {
        final String[] suppressedViolation = {
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolation = {
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "45:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "48:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "51:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressedWithParser(getPath("InputSuppressionCommentFilterSuppressById5.java"),
                expectedViolation, suppressedViolation);
    }

    @Test
    public void testFindNearestMatchDontAllowSameColumn() {
        final SuppressionCommentFilter suppressionCommentFilter = new SuppressionCommentFilter();
        final FileContents contents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("//CHECKSTYLE:OFF: ConstantNameCheck", "line2")));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, "filename",
                new Violation(1, null, null, null, null, Object.class, null), null);
        final boolean result = suppressionCommentFilter.accept(dummyEvent);
        assertWithMessage("Filter should not accept event")
            .that(result)
            .isFalse();
    }

    @Test
    public void testTagsAreClearedEachRun() {
        final SuppressionCommentFilter suppressionCommentFilter = new SuppressionCommentFilter();
        final List<?> tags1 = getTagsAfterExecution(suppressionCommentFilter,
                "filename1", "//CHECKSTYLE:OFF", "line2");
        assertWithMessage("Invalid tags size")
            .that(tags1)
            .hasSize(1);
        final List<?> tags2 = getTagsAfterExecution(suppressionCommentFilter,
                "filename2", "No comments in this file");
        assertWithMessage("Invalid tags size")
            .that(tags2)
            .isEmpty();
    }

    private static List<Comparable<Object>> getTagsAfterExecutionOnDefaultFilter(String... lines) {
        return getTagsAfterExecution(new SuppressionCommentFilter(), "filename", lines);
    }

    /**
     * Calls the filter with a minimal set of inputs and returns a list of
     * {@link SuppressionCommentFilter} internal type {@code Tag}.
     * Our goal is 100% test coverage, for this we use white-box testing.
     * So we need access to the implementation details. For this reason,
     * it is necessary to use reflection to gain access to the inner field here.
     *
     * @return {@code Tag} list
     */
    private static List<Comparable<Object>> getTagsAfterExecution(SuppressionCommentFilter filter,
            String filename, String... lines) {
        final FileContents contents = new FileContents(
                new FileText(new File(filename), Arrays.asList(lines)));
        for (int lineNo = 0; lineNo < lines.length; lineNo++) {
            final int colNo = lines[lineNo].indexOf("//");
            if (colNo >= 0) {
                contents.reportSingleLineComment(lineNo + 1, colNo);
            }
        }
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, filename,
                new Violation(1, null, null, null, null, Object.class, ""), null);
        filter.accept(dummyEvent);
        return TestUtil.getInternalState(filter, "tags");
    }

}
