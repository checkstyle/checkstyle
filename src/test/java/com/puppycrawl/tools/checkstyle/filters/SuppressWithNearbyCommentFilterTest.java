////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.checks.FileContentsHolder;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SuppressWithNearbyCommentFilterTest
    extends BaseCheckTestSupport {
    private static String[] sAllMessages = {
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

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = {
        };
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
    public void testCheckCPP() throws Exception {
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
    public void testUsingAVariableMessage() throws Exception {
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
    public void testUsingAVariableCheckOnNextLine() throws Exception {
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
    public void testUsingAVariableCheckOnPreviousLine() throws Exception {
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

    public static DefaultConfiguration createFilterConfig(Class<?> classObj) {
        return new DefaultConfiguration(classObj.getName());
    }

    protected void verifySuppressed(Configuration filterConfig,
                                    String[] suppressed)
        throws Exception {
        verify(createChecker(filterConfig),
               getPath("filters/InputSuppressWithNearbyCommentFilter.java"),
               removeSuppressed(sAllMessages, suppressed));
    }

    @Override
    protected Checker createChecker(Configuration filterConfig)
            throws CheckstyleException, UnsupportedEncodingException {
        final DefaultConfiguration checkerConfig =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createCheckConfig(TreeWalker.class);
        checksConfig.addChild(createCheckConfig(FileContentsHolder.class));
        checksConfig.addChild(createCheckConfig(MemberNameCheck.class));
        checksConfig.addChild(createCheckConfig(ConstantNameCheck.class));
        checksConfig.addChild(createCheckConfig(IllegalCatchCheck.class));
        checkerConfig.addChild(checksConfig);
        if (filterConfig != null) {
            checkerConfig.addChild(filterConfig);
        }
        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(stream));
        return checker;
    }

    private String[] removeSuppressed(String[] from, String[] remove) {
        final Collection<String> coll =
            Lists.newArrayList(Arrays.asList(from));
        coll.removeAll(Arrays.asList(remove));
        return coll.toArray(new String[coll.size()]);
    }

    @Test(expected = ConversionException.class)
    public void testInvalidInfluenceFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("influenceFormat", "a");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test(expected = ConversionException.class)
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("checkFormat", "a[l");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testAcceptNullLocalizedMessage() {
        final SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        final AuditEvent auditEvent = new AuditEvent(this);
        Assert.assertTrue(filter.accept(auditEvent));
    }

    @Test
    public void testAcceptNullFileContents() {
        final LocalizedMessage message =
            new LocalizedMessage(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                this.getClass(), null);
        final AuditEvent auditEvent = new AuditEvent(this, "Test.java", message);
        SuppressWithNearbyCommentFilter filter = new SuppressWithNearbyCommentFilter();
        Assert.assertTrue(filter.accept(auditEvent));
    }

    @Test
    public void testToStringOfTagClass() {
        SuppressWithNearbyCommentFilter.Tag tag = new SuppressWithNearbyCommentFilter.Tag(
                "text", 7, new SuppressWithNearbyCommentFilter()
        );
        assertEquals("Tag[lines=[7 to 7]; text='text']", tag.toString());
    }

    @Test
    public void testUsingTagMessageRegexp() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressWithNearbyCommentFilter.class);
        filterConfig.addAttribute("commentFormat", "SUPPRESS CHECKSTYLE (\\w+)");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "^$1 ololo*$");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }
}
