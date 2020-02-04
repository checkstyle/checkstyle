////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class SuppressionCommentFilterTest
    extends AbstractModuleTestSupport {

    private static final String[] ALL_MESSAGES = {
        "13:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "I", "^[a-z][a-zA-Z0-9]*$"),
        "16:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
        "19:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "K", "^[a-z][a-zA-Z0-9]*$"),
        "22:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
        "23:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "27:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "M2", "^[a-z][a-zA-Z0-9]*$"),
        "28:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "32:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "P", "^[a-z][a-zA-Z0-9]*$"),
        "35:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "Q", "^[a-z][a-zA-Z0-9]*$"),
        "38:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "R", "^[a-z][a-zA-Z0-9]*$"),
        "39:30: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        "43:17: "
            + getCheckMessage(AbstractNameCheck.class,
                MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
        "64:23: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "71:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "77:11: "
            + getCheckMessage(IllegalCatchCheck.class,
                IllegalCatchCheck.MSG_KEY, "RuntimeException"),
        "78:11: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        "86:31: "
            + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter";
    }

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    // Suppress all checks between default comments
    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        final String[] suppressed = {
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "43:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
            "64:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "71:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "86:31: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkC", "false");
        final String[] suppressed = {
            "43:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
            "64:23: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
            "71:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckCpp() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkCPP", "false");
        final String[] suppressed = {
            "16:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "J", "^[a-z][a-zA-Z0-9]*$"),
            "86:31: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    // Suppress all checks between CS_OFF and CS_ON
    @Test
    public void testOffFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CS_OFF");
        filterConfig.addAttribute("onCommentFormat", "CS_ON");
        final String[] suppressed = {
            "32:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "P", "^[a-z][a-zA-Z0-9]*$"),
            "38:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "R", "^[a-z][a-zA-Z0-9]*$"),
            "39:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "42:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "T", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    // Test suppression of checks of only one type
    //  Suppress only ConstantNameCheck between CS_OFF and CS_ON
    @Test
    public void testOffFormatCheck() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CS_OFF");
        filterConfig.addAttribute("onCommentFormat", "CS_ON");
        filterConfig.addAttribute("checkFormat", "ConstantNameCheck");
        final String[] suppressed = {
            "39:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "s", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testArgumentSuppression() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "IllegalCatchCheck OFF\\: (\\w+)");
        filterConfig.addAttribute("onCommentFormat", "IllegalCatchCheck ON\\: (\\w+)");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        // -@cs[CheckstyleTestMakeup] need to test dynamic property
        filterConfig.addAttribute("messageFormat",
                "^" + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "$1")
                        + "*$");
        final String[] suppressed = {
            "78:11: "
                + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY, "Exception"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testExpansion() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF\\: ([\\w\\|]+)");
        filterConfig.addAttribute("onCommentFormat", "CSON\\: ([\\w\\|]+)");
        filterConfig.addAttribute("checkFormat", "$1");
        final String[] suppressed = {
            "22:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "L", "^[a-z][a-zA-Z0-9]*$"),
            "23:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "m", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "28:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "n", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("onCommentFormat", "UNUSED ON\\: (\\w+)");
        filterConfig.addAttribute("offCommentFormat", "UNUSED OFF\\: (\\w+)");
        filterConfig.addAttribute("checkFormat", "Unused");
        filterConfig.addAttribute("messageFormat", "^Unused \\w+ '$1'.$");
        final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    private void verifySuppressed(Configuration moduleConfig,
            String... aSuppressed)
            throws Exception {
        verifySuppressed(moduleConfig, getPath("InputSuppressionCommentFilter.java"),
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

        verify(checkerConfig, fileName,
                removeSuppressed(expectedViolations, suppressedViolations));
    }

    private static String[] removeSuppressed(String[] from, String... remove) {
        final Collection<String> coll = Arrays.stream(from).collect(Collectors.toList());
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        final Object tag = getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:OFF").get(0);
        final EqualsVerifierReport ev = EqualsVerifier.forClass(tag.getClass())
                .usingGetClass().report();
        assertEquals(EqualsVerifierReport.SUCCESS, ev, "Error: " + ev.getMessage());
    }

    @Test
    public void testToStringOfTagClass() {
        final Object tag = getTagsAfterExecutionOnDefaultFilter("//CHECKSTYLE:OFF").get(0);
        assertEquals(
                "Tag[text='CHECKSTYLE:OFF', line=1, column=0, type=OFF,"
                    + " tagCheckRegexp=.*, tagMessageRegexp=null, tagIdRegexp=null]",
                    tag.toString(), "Invalid toString result");
    }

    @Test
    public void testToStringOfTagClassWithMessage() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        filter.setMessageFormat(".*");
        final Object tag =
                getTagsAfterExecution(filter, "filename", "//CHECKSTYLE:ON").get(0);
        assertEquals(
                "Tag[text='CHECKSTYLE:ON', line=1, column=0, type=ON,"
                + " tagCheckRegexp=.*, tagMessageRegexp=.*, tagIdRegexp=null]", tag.toString(),
                "Invalid toString result");
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

        assertTrue(tag1.compareTo(tag2) < 0, "Invalid comparing result");
        assertTrue(tag2.compareTo(tag1) > 0, "Invalid comparing result");
        assertTrue(tag1.compareTo(tag3) < 0, "Invalid comparing result");
        assertTrue(tag3.compareTo(tag1) > 0, "Invalid comparing result");
        final int actual = tag1.compareTo(tag4);
        assertEquals(0, actual, "Invalid comparing result");
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkFormat", "e[l");

        try {
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("unable to parse expanded comment e[l", cause.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testInvalidMessageFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("messageFormat", "e[l");

        try {
            final String[] suppressed = CommonUtil.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertEquals("unable to parse expanded comment e[l", cause.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testAcceptNullLocalizedMessage() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        final FileContents contents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("//CHECKSTYLE:OFF: ConstantNameCheck", "line2")));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent auditEvent =
                new TreeWalkerAuditEvent(contents, null, null, null);
        assertTrue(filter.accept(auditEvent), "Filter should accept audit event");
        assertNull(auditEvent.getFileName(), "File name should not be null");
    }

    @Test
    public void testAcceptNullFileContents() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
        final FileContents contents = null;
        final TreeWalkerAuditEvent auditEvent = new TreeWalkerAuditEvent(contents, null,
                new LocalizedMessage(1, null, null, null, null, Object.class, null), null);
        assertTrue(filter.accept(auditEvent), "Filter should accept audit event");
    }

    @Test
    public void testSuppressByCheck() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(\\w+\\)");
        filterConfig.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterConfig.addAttribute("checkFormat", "MemberNameCheck");
        final String[] suppressedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "15:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "21:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressed(filterConfig, getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressById() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(\\w+\\)");
        filterConfig.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterConfig.addAttribute("idFormat", "$1");
        final String[] suppressedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "15:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "21:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressed(filterConfig, getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndId() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(\\w+\\)");
        filterConfig.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterConfig.addAttribute("checkFormat", "MemberNameCheck");
        filterConfig.addAttribute("idFormat", "$1");
        final String[] suppressedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "15:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "21:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressed(filterConfig, getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByIdAndMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(allow (\\w+)\\)");
        filterConfig.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterConfig.addAttribute("idFormat", "$1");
        filterConfig.addAttribute("messageFormat", "$2");
        final String[] suppressedViolationMessages = {
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "15:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "21:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressed(filterConfig, getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testSuppressByCheckAndMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createModuleConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF (\\w+) \\(allow (\\w+)\\)");
        filterConfig.addAttribute("onCommentFormat", "CSON (\\w+)");
        filterConfig.addAttribute("checkFormat", "MemberNameCheck");
        filterConfig.addAttribute("messageFormat", "$2");
        final String[] suppressedViolationMessages = {
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            };
        final String[] expectedViolationMessages = {
            "6:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "A1", "^[a-z][a-zA-Z0-9]*$"),
            "9:30: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "abc", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
            "12:9: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "line_length", "^[a-z][a-zA-Z0-9]*$"),
            "15:18: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "ID", "^[a-z][a-zA-Z0-9]*$"),
            "18:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "DEF", "^[a-z][a-zA-Z0-9]*$"),
            "21:17: "
                + getCheckMessage(AbstractNameCheck.class,
                    MSG_INVALID_PATTERN, "XYZ", "^[a-z][a-zA-Z0-9]*$"),
            };

        verifySuppressed(filterConfig, getPath("InputSuppressionCommentFilterSuppressById.java"),
                expectedViolationMessages, suppressedViolationMessages);
    }

    @Test
    public void testFindNearestMatchDontAllowSameColumn() {
        final SuppressionCommentFilter suppressionCommentFilter = new SuppressionCommentFilter();
        final FileContents contents = new FileContents(new FileText(new File("filename"),
                Arrays.asList("//CHECKSTYLE:OFF: ConstantNameCheck", "line2")));
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, "filename",
                new LocalizedMessage(1, null, null, null, null, Object.class, null), null);
        final boolean result = suppressionCommentFilter.accept(dummyEvent);
        assertFalse(result, "Filter should not accept event");
    }

    @Test
    public void testTagsAreClearedEachRun() {
        final SuppressionCommentFilter suppressionCommentFilter = new SuppressionCommentFilter();
        final List<?> tags1 = getTagsAfterExecution(suppressionCommentFilter,
                "filename1", "//CHECKSTYLE:OFF", "line2");
        assertEquals(1, tags1.size(), "Invalid tags size");
        final List<?> tags2 = getTagsAfterExecution(suppressionCommentFilter,
                "filename2", "No comments in this file");
        assertEquals(0, tags2.size(), "Invalid tags size");
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
                new LocalizedMessage(1, null, null, null, null, Object.class, ""), null);
        filter.accept(dummyEvent);
        return Whitebox.getInternalState(filter, "tags");
    }

}
