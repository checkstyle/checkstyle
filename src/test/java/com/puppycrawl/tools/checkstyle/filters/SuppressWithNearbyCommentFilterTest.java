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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import nl.jqno.equalsverifier.EqualsVerifier;

public class SuppressWithNearbyCommentFilterTest
    extends AbstractModuleTestSupport {
    private static final String[] ALL_MESSAGES = {
        "14:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "15:17: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "16:59: Name 'A3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "18:17: Name 'B1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "19:17: Name 'B2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "20:59: Name 'B3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "22:17: Name 'C1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "24:17: Name 'C2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "25:17: Name 'C3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "27:17: Name 'D1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "28:17: Name 'D2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "30:17: Name 'D3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "32:30: Name 'e1' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "33:17: Name 'E2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "34:17: Name 'E3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "35:30: Name 'e4' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "36:17: Name 'E5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "37:30: Name 'e6' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "38:17: Name 'E7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "39:17: Name 'E8' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "40:30: Name 'e9' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "64:23: Catching 'Exception' is not allowed.",
        "66:23: Catching 'Throwable' is not allowed.",
        "73:11: Catching 'Exception' is not allowed.",
        "80:59: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "81:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
    };

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters";
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
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        final String[] suppressed = {
            "14:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "15:17: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "16:59: Name 'A3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "18:17: Name 'B1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "19:17: Name 'B2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:59: Name 'B3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "80:59: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("checkC", "false");
        final String[] suppressed = {
            "14:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "18:17: Name 'B1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckCpp() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("checkCPP", "false");
        final String[] suppressed = {
            "15:17: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "16:59: Name 'A3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "19:17: Name 'B2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:59: Name 'B3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "80:59: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "ALLOW CATCH (\\w+) BECAUSE");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "-1");
        final String[] suppressed = {
            "66:23: Catching 'Throwable' is not allowed.",
            "73:11: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableCheckOnNextLine() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) ON NEXT LINE");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "1");
        final String[] suppressed = {
            "24:17: Name 'C2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testUsingVariableCheckOnPreviousLine() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) ON PREVIOUS LINE");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "-1");
        final String[] suppressed = {
            "28:17: Name 'D2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testVariableCheckOnVariableNumberOfLines() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "ALLOW (\\w+) UNTIL THIS LINE([+-]\\d+)");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "$2");
        final String[] suppressed = {
            "35:30: Name 'e4' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "36:17: Name 'E5' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "38:17: Name 'E7' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "39:17: Name 'E8' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        EqualsVerifier.forClass(SuppressWithNearbyCommentFilter.Tag.class).usingGetClass().verify();
    }

    private static DefaultConfiguration createFilterConfig(Class<?> classObj) {
        return new DefaultConfiguration(classObj.getName());
    }

    private void verifySuppressed(Configuration filterConfig,
            String... suppressed)
            throws Exception {
        verify(createChecker(filterConfig),
               getPath("InputSuppressWithNearbyCommentFilter.java"),
               removeSuppressed(ALL_MESSAGES, suppressed));
    }

    @Override
    public Checker createChecker(Configuration moduleConfig)
            throws CheckstyleException {
        final DefaultConfiguration checkerConfig =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createModuleConfig(TreeWalker.class);
        final DefaultConfiguration memberNameCheckConfig =
                createModuleConfig(MemberNameCheck.class);
        memberNameCheckConfig.addAttribute("id", "ignore");
        checksConfig.addChild(memberNameCheckConfig);
        final DefaultConfiguration constantNameCheckConfig =
            createModuleConfig(ConstantNameCheck.class);
        constantNameCheckConfig.addAttribute("id", null);
        checksConfig.addChild(constantNameCheckConfig);
        checksConfig.addChild(createModuleConfig(IllegalCatchCheck.class));
        checkerConfig.addChild(checksConfig);
        if (moduleConfig != null) {
            checksConfig.addChild(moduleConfig);
        }
        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());
        return checker;
    }

    private static String[] removeSuppressed(String[] from, String... remove) {
        final Collection<String> coll = Arrays.stream(from).collect(Collectors.toList());
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(new String[coll.size()]);
    }

    @Test
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("influenceFormat", "a");

        try {
            final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
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
                createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("influenceFormat", "1");

        final String[] suppressed = {
            "14:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "15:17: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "16:59: Name 'A3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "18:17: Name 'B1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "19:17: Name 'B2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "20:59: Name 'B3' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "80:59: Name 'A2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "81:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("checkFormat", "a[l");

        try {
            final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
            verifySuppressed(filterConfig, suppressed);
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
        final TreeWalkerAuditEvent auditEvent = new TreeWalkerAuditEvent(null, null, null);
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
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "SUPPRESS CHECKSTYLE (\\w+)");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "^$1 ololo*$");
        final String[] suppressed = CommonUtils.EMPTY_STRING_ARRAY;
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testSuppressById() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "@cs-: (\\w+) \\(\\w+\\)");
        filterConfig.addAttribute("checkFormat", "$1");
        filterConfig.addAttribute("influenceFormat", "0");
        final String[] suppressedViolationMessages = {
            "5:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "9:9: Name 'line_length' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        final String[] expectedViolationMessages = {
            "5:17: Name 'A1' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "7:30: Name 'abc' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "9:9: Name 'line_length' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "11:18: Name 'ID' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verify(createChecker(filterConfig),
            getPath("InputSuppressByIdWithNearbyCommentFilter.java"),
            removeSuppressed(expectedViolationMessages, suppressedViolationMessages));
    }

    @Test
    public void testTagsAreClearedEachRun() {
        final SuppressWithNearbyCommentFilter suppressionCommentFilter =
                new SuppressWithNearbyCommentFilter();
        final FileContents contents =
                new FileContents("filename", "//SUPPRESS CHECKSTYLE ignore", "line2");
        contents.reportSingleLineComment(1, 0);
        final TreeWalkerAuditEvent dummyEvent = new TreeWalkerAuditEvent(contents, "filename",
                new LocalizedMessage(1, null, null, null, null, Object.class, null));
        suppressionCommentFilter.accept(dummyEvent);
        final FileContents contents2 =
                new FileContents("filename2", "some line", "//SUPPRESS CHECKSTYLE ignore");
        contents2.reportSingleLineComment(2, 0);
        final TreeWalkerAuditEvent dummyEvent2 = new TreeWalkerAuditEvent(contents2, "filename",
                new LocalizedMessage(1, null, null, null, null, Object.class, null));
        suppressionCommentFilter.accept(dummyEvent2);
        final List<SuppressionCommentFilter.Tag> tags =
                Whitebox.getInternalState(suppressionCommentFilter, "tags");
        assertEquals("Invalid tags size", 1, tags.size());
    }
}
