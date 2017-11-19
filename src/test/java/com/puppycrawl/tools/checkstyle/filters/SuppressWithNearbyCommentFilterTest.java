////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import nl.jqno.equalsverifier.EqualsVerifier;

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
        final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
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
        filterConfig.addAttribute("checkC", "false");
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
        filterConfig.addAttribute("checkCPP", "false");
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
        filterConfig.addAttribute("commentFormat", "ALLOW CATCH (\\w+) BECAUSE");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "-1");
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
    public void testUsingVariableCheckOnNextLine() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) ON NEXT LINE");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "1");
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
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) ON PREVIOUS LINE");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "-1");
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
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) UNTIL THIS LINE([+-]\\d+)");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "$2");
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
        EqualsVerifier.forClass(SuppressWithNearbyCommentFilter.Tag.class).usingGetClass().verify();
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
        memberNameCheckConfig.addAttribute("id", "ignore");

        final DefaultConfiguration constantNameCheckConfig =
            createModuleConfig(ConstantNameCheck.class);
        constantNameCheckConfig.addAttribute("id", null);

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
        return coll.toArray(new String[coll.size()]);
    }

    @Test
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("influenceFormat", "a");

        try {
            final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("Invalid exception message", "unable to parse influence"
                + " from 'SUPPRESS CHECKSTYLE MemberNameCheck' using a", cause.getMessage());
        }
    }

    @Test
    public void testInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
                createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("influenceFormat", "1");

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
        filterConfig.addAttribute("checkFormat", "a[l");

        try {
            final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("Invalid exception message",
                "unable to parse expanded comment a[l", cause.getMessage());
        }
    }

    @Test
    public void testAcceptNullLocalizedMessage() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final TreeWalkerAuditEvent auditEvent = new TreeWalkerAuditEvent(null, null, null, null);
        assertTrue("Filter should accept null localized message", filter.accept(auditEvent));
    }

    @Test
    public void testToStringOfTagClass() {
        final SuppressWithNearbyCommentFilter.Tag tag = new SuppressWithNearbyCommentFilter.Tag(
                "text", 7, new SuppressWithNearbyCommentFilter()
        );
        assertEquals("Invalid toString result",
            "Tag[text='text', firstLine=7, lastLine=7, "
                    + "tagCheckRegexp=.*, tagMessageRegexp=null]", tag.toString());
    }

    @Test
    public void testUsingTagMessageRegexp() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "SUPPRESS CHECKSTYLE (\\w+)");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "^$1 ololo*$");
        final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testSuppressById() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "0");
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
        };

        verifySuppressed(filterConfig,
            getPath("InputSuppressWithNearbyCommentFilterById.java"),
            expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testTagsAreClearedEachRun() {
        final SuppressWithNearbyCommentFilter suppressionCommentFilter =
                new SuppressWithNearbyCommentFilter();
        final FileContents contents =
                new FileContents("filename", "//SUPPRESS CHECKSTYLE ignore", "line2");
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, "filename",
                new LocalizedMessage(1, null, null, null, null, Object.class, null), null);
        suppressionCommentFilter.accept(dummyEvent);
        final FileContents contents2 =
                new FileContents("filename2", "some line", "//SUPPRESS CHECKSTYLE ignore");
        contents2.reportSingleLineComment(2, 0);
        final TreeWalkerAuditEvent dummyEvent2 = new TreeWalkerAuditEvent(contents2, "filename",
                new LocalizedMessage(1, null, null, null, null, Object.class, null), null);
        suppressionCommentFilter.accept(dummyEvent2);
        final List<SuppressionCommentFilter.Tag> tags =
                Whitebox.getInternalState(suppressionCommentFilter, "tags");
        assertEquals("Invalid tags size", 1, tags.size());
    }
}
