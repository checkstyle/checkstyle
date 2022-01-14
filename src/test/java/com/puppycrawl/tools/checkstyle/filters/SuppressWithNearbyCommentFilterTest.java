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
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressWithNearbyCommentFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "14:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
        "15:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
        "16:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z0-9]*$"),
        "18:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z0-9]*$"),
        "19:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z0-9]*$"),
        "20:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z0-9]*$"),
        "22:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C1", "^[a-z][a-zA-Z0-9]*$"),
        "24:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C2", "^[a-z][a-zA-Z0-9]*$"),
        "25:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "C3", "^[a-z][a-zA-Z0-9]*$"),
        "27:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D1", "^[a-z][a-zA-Z0-9]*$"),
        "28:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D2", "^[a-z][a-zA-Z0-9]*$"),
        "30:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "D3", "^[a-z][a-zA-Z0-9]*$"),
        "32:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e1", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "33:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E2", "^[a-z][a-zA-Z0-9]*$"),
        "34:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E3", "^[a-z][a-zA-Z0-9]*$"),
        "35:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "36:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E5", "^[a-z][a-zA-Z0-9]*$"),
        "37:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e6", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "38:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E7", "^[a-z][a-zA-Z0-9]*$"),
        "39:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "E8", "^[a-z][a-zA-Z0-9]*$"),
        "40:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "e9", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "64:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "66:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Throwable"),
        "73:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "80:59: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
        "81:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbycommentfilter";
    }

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        final String[] suppressed = {
            "14:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
            "16:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z0-9]*$"),
            "19:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z0-9]*$"),
            "20:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z0-9]*$"),
            "80:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("checkC", "false");
        final String[] suppressed = {
            "14:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckCpp() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("checkCPP", "false");
        final String[] suppressed = {
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
            "16:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z0-9]*$"),
            "19:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z0-9]*$"),
            "20:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z0-9]*$"),
            "80:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "ALLOW CATCH (\\w+) BECAUSE");
        filterConfig.addProperty("checkFormat", "IllegalCatchCheck");
        filterConfig.addProperty("messageFormat", "$1");
        filterConfig.addProperty("influenceFormat", "-1");
        final String[] suppressed = {
            "66:23: "
                + getCheckMessage(IllegalCatchCheck.class,
                    IllegalCatchCheck.MSG_KEY, "Throwable"),
            "73:11: "
                + getCheckMessage(IllegalCatchCheck.class,
                    IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingNonMatchingVariableMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "ALLOW CATCH (\\w+) BECAUSE");
        filterConfig.addProperty("checkFormat", "IllegalCatchCheck");
        filterConfig.addProperty("messageFormat", "NonMatchingMessage");
        filterConfig.addProperty("influenceFormat", "-1");
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableCheckOnNextLine() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "ALLOW (\\w+) ON NEXT LINE");
        filterConfig.addProperty("checkFormat", "$1");
        filterConfig.addProperty("influenceFormat", "1");
        final String[] suppressed = {
            "24:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "C2", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableCheckOnPreviousLine() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "ALLOW (\\w+) ON PREVIOUS LINE");
        filterConfig.addProperty("checkFormat", "$1");
        filterConfig.addProperty("influenceFormat", "-1");
        final String[] suppressed = {
            "28:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "D2", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testVariableCheckOnVariableNumberOfLines() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "ALLOW (\\w+) UNTIL THIS LINE([+-]\\d+)");
        filterConfig.addProperty("checkFormat", "$1");
        filterConfig.addProperty("influenceFormat", "$2");
        final String[] suppressed = {
            "35:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "e4", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "36:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E5", "^[a-z][a-zA-Z0-9]*$"),
            "38:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E7", "^[a-z][a-zA-Z0-9]*$"),
            "39:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "E8", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
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

    private void verifySuppressed(Configuration moduleConfig,
            String... aSuppressed)
            throws Exception {
        verifySuppressed(moduleConfig, getPath("InputSuppressWithNearbyCommentFilter.java"),
               ALL_MESSAGES, aSuppressed);
    }

    private void verifySuppressed(Configuration moduleConfig, String fileName,
            String[] expectedViolations, String... suppressedViolations) throws Exception {
        final DefaultConfiguration memberNameCheckConfig =
                createModuleConfig(MemberNameCheck.class);
        memberNameCheckConfig.addProperty("id", "ignore");

        final DefaultConfiguration constantNameCheckConfig =
            createModuleConfig(ConstantNameCheck.class);
        constantNameCheckConfig.addProperty("id", null);

        final DefaultConfiguration treewalkerConfig = createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(memberNameCheckConfig);
        treewalkerConfig.addChild(constantNameCheckConfig);
        treewalkerConfig.addChild(createModuleConfig(IllegalCatchCheck.class));

        if (moduleConfig != null) {
            treewalkerConfig.addChild(moduleConfig);
        }

        final DefaultConfiguration checkerConfig = createRootConfig(treewalkerConfig);

        verify(checkerConfig,
                fileName,
                removeSuppressed(expectedViolations, suppressedViolations));
    }

    private static String[] removeSuppressed(String[] from, String... remove) {
        final Collection<String> coll = Arrays.stream(from).collect(Collectors.toList());
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("influenceFormat", "a");

        try {
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat().hasMessageThat()
                .isEqualTo("unable to parse influence"
                        + " from 'SUPPRESS CHECKSTYLE MemberNameCheck' using a");
        }
    }

    @Test
    public void testInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
                createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("influenceFormat", "+1");

        final String[] suppressed = {
            "14:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
            "16:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A3", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B1", "^[a-z][a-zA-Z0-9]*$"),
            "19:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B2", "^[a-z][a-zA-Z0-9]*$"),
            "20:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "B3", "^[a-z][a-zA-Z0-9]*$"),
            "80:59: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A2", "^[a-z][a-zA-Z0-9]*$"),
            "81:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("checkFormat", "a[l");

        try {
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
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
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "SUPPRESS CHECKSTYLE (\\w+)");
        filterConfig.addProperty("checkFormat", "IllegalCatchCheck");
        filterConfig.addProperty("messageFormat", "^$1 ololo*$");
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testSuppressByCheck() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addProperty("checkFormat", "MemberNameCheck");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressById() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addProperty("idFormat", "$1");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndId() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addProperty("checkFormat", "MemberNameCheck");
        filterConfig.addProperty("idFormat", "$1");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndNonMatchingId() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addProperty("checkFormat", "MemberNameCheck");
        filterConfig.addProperty("idFormat", "emberNa");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "13:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void tesSuppressByIdAndMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(allow (\\w+)\\)");
        filterConfig.addProperty("idFormat", "$1");
        filterConfig.addProperty("messageFormat", "$2");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "13:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void tesSuppressByCheckAndMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addProperty("commentFormat", "@cs-: (\\w+) \\(allow (\\w+)\\)");
        filterConfig.addProperty("checkFormat", "MemberNameCheck");
        filterConfig.addProperty("messageFormat", "$2");
        filterConfig.addProperty("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedViolationMessages = {
            "5:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "7:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "9:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "11:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "13:57: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID3", "^[a-z][a-zA-Z0-9]*$"),
            "15:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
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
