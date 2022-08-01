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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
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
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithNearbyCommentFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "46:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
        "49:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
        "50:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z\\d]*$"),
        "53:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z\\d]*$"),
        "56:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z\\d]*$"),
        "57:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z\\d]*$"),
        "59:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C1", "^[a-z][a-zA-Z\\d]*$"),
        "61:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C2", "^[a-z][a-zA-Z\\d]*$"),
        "62:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C3", "^[a-z][a-zA-Z\\d]*$"),
        "64:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D1", "^[a-z][a-zA-Z\\d]*$"),
        "65:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D2", "^[a-z][a-zA-Z\\d]*$"),
        "67:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D3", "^[a-z][a-zA-Z\\d]*$"),
        "69:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e1", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
        "70:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E2", "^[a-z][a-zA-Z\\d]*$"),
        "73:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E3", "^[a-z][a-zA-Z\\d]*$"),
        "74:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e4", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
        "75:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E5", "^[a-z][a-zA-Z\\d]*$"),
        "76:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e6", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
        "77:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E7", "^[a-z][a-zA-Z\\d]*$"),
        "78:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E8", "^[a-z][a-zA-Z\\d]*$"),
        "80:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e9", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
        "100:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "102:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Throwable"),
        "109:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "117:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
        "118:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbycommentfilter";
    }

    @Test
    public void testNone() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expected = {
            "36:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "39:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "40:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z\\d]*$"),
            "43:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z\\d]*$"),
            "46:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z\\d]*$"),
            "47:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z\\d]*$"),
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "C1", "^[a-z][a-zA-Z\\d]*$"),
            "51:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "C2", "^[a-z][a-zA-Z\\d]*$"),
            "52:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "C3", "^[a-z][a-zA-Z\\d]*$"),
            "54:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "D1", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "D2", "^[a-z][a-zA-Z\\d]*$"),
            "57:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "D3", "^[a-z][a-zA-Z\\d]*$"),
            "59:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e1", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "60:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E2", "^[a-z][a-zA-Z\\d]*$"),
            "63:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E3", "^[a-z][a-zA-Z\\d]*$"),
            "64:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e4", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "65:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E5", "^[a-z][a-zA-Z\\d]*$"),
            "66:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e6", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "67:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E7", "^[a-z][a-zA-Z\\d]*$"),
            "68:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E8", "^[a-z][a-zA-Z\\d]*$"),
            "70:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e9", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "90:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "92:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Throwable"),
            "99:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "107:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "108:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterWithoutFilter.java"), expected,
            suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] suppressed = {
            "46:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "50:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z\\d]*$"),
            "53:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z\\d]*$"),
            "56:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z\\d]*$"),
            "57:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z\\d]*$"),
            "117:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilter.java"), suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final String[] suppressed = {
            "46:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "53:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterCheckC.java"), suppressed);
    }

    @Test
    public void testCheckCpp() throws Exception {
        final String[] suppressed = {
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "50:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z\\d]*$"),
            "56:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z\\d]*$"),
            "57:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z\\d]*$"),
            "117:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterCheckCpp.java"), suppressed);
    }

    @Test
    public void testUsingVariableMessage() throws Exception {
        final String[] suppressed = {
            "102:23: "
                + getCheckMessage(IllegalCatchCheck.class,
                    IllegalCatchCheck.MSG_KEY, "Throwable"),
            "109:11: "
                + getCheckMessage(IllegalCatchCheck.class,
                    IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterUsingVariableMessage.java"), suppressed);
    }

    @Test
    public void testUsingNonMatchingVariableMessage() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterUsingNonMatchingVariableMessage.java"),
            suppressed);
    }

    @Test
    public void testUsingVariableCheckOnNextLine() throws Exception {
        final String[] suppressed = {
            "61:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "C2", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterUsingVariableCheckOnNextLine.java"),
            suppressed);
    }

    @Test
    public void testUsingVariableCheckOnPreviousLine() throws Exception {
        final String[] suppressed = {
            "65:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "D2", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterUsingVariableCheckOnPreviousLine.java"),
            suppressed);
    }

    @Test
    public void testVariableCheckOnVariableNumberOfLines() throws Exception {
        final String[] suppressed = {
            "74:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e4", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "75:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E5", "^[a-z][a-zA-Z\\d]*$"),
            "77:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E7", "^[a-z][a-zA-Z\\d]*$"),
            "78:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E8", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterVariableCheckOnVariableNumberOfLines"
                        + ".java"),
            suppressed);
    }

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final Object tag =
                getTagsAfterExecution(filter, "filename", "//SUPPRESS CHECKSTYLE ignore").get(0);
        final EqualsVerifierReport ev = EqualsVerifier
                .forClass(tag.getClass()).usingGetClass().report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    private void verifySuppressedWithParser(String fileName, String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, ALL_MESSAGES,
                                           removeSuppressed(ALL_MESSAGES, suppressed));
    }

    private void verifySuppressedWithParser(String fileName, String[] messages,
                                            String... suppressed)
            throws Exception {
        verifyFilterWithInlineConfigParser(fileName, messages,
                                           removeSuppressed(messages, suppressed));
    }

    @Test
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration treeWalkerConfig =
            createModuleConfig(TreeWalker.class);
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("influenceFormat", "a");
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        treeWalkerConfig.addChild(filterConfig);
        treeWalkerConfig.addChild(checkConfig);

        try {
            execute(treeWalkerConfig,
                    getPath("InputSuppressWithNearbyCommentFilterByCheckAndInfluence.java"));
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("unable to parse influence"
                        + " from 'SUPPRESS CHECKSTYLE MemberNameCheck' using a");
        }
    }

    @Test
    public void testInfluenceFormat() throws Exception {
        final String[] suppressed = {
            "46:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "49:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "50:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z\\d]*$"),
            "53:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z\\d]*$"),
            "56:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z\\d]*$"),
            "57:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z\\d]*$"),
            "117:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z\\d]*$"),
            "118:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
        };
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterInfluenceFormat.java"),
            suppressed);
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration treeWalkerConfig =
            createModuleConfig(TreeWalker.class);
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("checkFormat", "a[l");
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        treeWalkerConfig.addChild(filterConfig);
        treeWalkerConfig.addChild(checkConfig);

        try {
            execute(treeWalkerConfig,
                    getPath("InputSuppressWithNearbyCommentFilterByCheckAndInfluence.java"));
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment a[l");
        }
    }

    @Test
    public void testAcceptNullViolation() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final FileContents contents = new FileContents(new FileText(new File("filename"),
                Collections.singletonList("//SUPPRESS CHECKSTYLE ignore")));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent auditEvent =
                new TreeWalkerAuditEvent(contents, null, null, null);
        assertWithMessage("Filter should accept null violation")
                .that(filter.accept(auditEvent))
                .isTrue();
    }

    @Test
    public void testAcceptNullFileContents() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final FileContents contents = null;
        final TreeWalkerAuditEvent auditEvent = new TreeWalkerAuditEvent(contents, null,
                new Violation(1, null, null, null, null, Object.class, null), null);
        assertWithMessage("Filter should accept audit event")
                .that(filter.accept(auditEvent))
                .isTrue();
    }

    @Test
    public void testToStringOfTagClass() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final Object tag =
                getTagsAfterExecution(filter, "filename", "//SUPPRESS CHECKSTYLE ignore").get(0);
        assertWithMessage("Invalid toString result")
            .that(tag.toString())
            .isEqualTo("Tag[text='SUPPRESS CHECKSTYLE ignore', firstLine=1, lastLine=1, "
                    + "tagCheckRegexp=.*, tagMessageRegexp=null, tagIdRegexp=null]");
    }

    @Test
    public void testToStringOfTagClassWithId() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        filter.setIdFormat(".*");
        final Object tag =
                getTagsAfterExecution(filter, "filename", "//SUPPRESS CHECKSTYLE ignore").get(0);
        assertWithMessage("Invalid toString result")
            .that(tag.toString())
            .isEqualTo("Tag[text='SUPPRESS CHECKSTYLE ignore', firstLine=1, lastLine=1, "
                    + "tagCheckRegexp=.*, tagMessageRegexp=null, tagIdRegexp=.*]");
    }

    @Test
    public void testUsingTagMessageRegexp() throws Exception {
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterUsingTagMessageRegexp.java"),
            suppressed);
    }

    @Test
    public void testSuppressByCheck() throws Exception {
        final String[] suppressedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
        };
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(getPath("InputSuppressWithNearbyCommentFilterByCheck.java"),
                                   expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressById() throws Exception {
        final String[] suppressedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
        };
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(getPath("InputSuppressWithNearbyCommentFilterById.java"),
                                   expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndId() throws Exception {
        final String[] suppressedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
        };
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterByCheckAndId.java"),
                                   expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndNonMatchingId() throws Exception {
        final String[] suppressedViolationMessages = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterByCheckAndNonMatchingId.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void tesSuppressByIdAndMessage() throws Exception {
        final String[] suppressedViolationMessages = {
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
        };
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterByIdAndMessage.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void tesSuppressByCheckAndMessage() throws Exception {
        final String[] suppressedViolationMessages = {
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
        };
        final String[] expectedViolationMessages = {
            "41:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z\\d]*$"),
            "44:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z\\d]*(_[A-Z\\d]+)*$"),
            "47:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z\\d]*$"),
            "50:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z\\d]*$"),
            "52:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z\\d]*$"),
            "55:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z\\d]*$"),
            "58:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z\\d]*$"),
        };

        verifySuppressedWithParser(
            getPath("InputSuppressWithNearbyCommentFilterByCheckAndMessage.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testTagsAreClearedEachRun() {
        final SuppressWithNearbyCommentFilter suppressionCommentFilter =
                new SuppressWithNearbyCommentFilter();
        final List<?> tags1 = getTagsAfterExecution(suppressionCommentFilter,
                "filename1", "//SUPPRESS CHECKSTYLE ignore this");
        assertWithMessage("Invalid tags size")
            .that(tags1)
            .hasSize(1);
        final List<?> tags2 = getTagsAfterExecution(suppressionCommentFilter,
                "filename2", "No comments in this file");
        assertWithMessage("Invalid tags size")
            .that(tags2)
            .isEmpty();
    }

    /**
     * Calls the filter with a minimal set of inputs and returns a list of
     * {@link SuppressWithNearbyCommentFilter} internal type {@code Tag}.
     * Our goal is 100% test coverage, for this we use white-box testing.
     * So we need access to the implementation details. For this reason,
     * it is necessary to use reflection to gain access to the inner field here.
     *
     * @return {@code Tag} list
     */
    private static List<?> getTagsAfterExecution(SuppressWithNearbyCommentFilter filter,
            String filename, String... lines) {
        final FileContents contents = new FileContents(
                new FileText(new File(filename), Arrays.asList(lines)));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, filename,
                new Violation(1, null, null, null, null, Object.class, null), null);
        filter.accept(dummyEvent);
        return TestUtil.getInternalState(filter, "tags");
    }

}
