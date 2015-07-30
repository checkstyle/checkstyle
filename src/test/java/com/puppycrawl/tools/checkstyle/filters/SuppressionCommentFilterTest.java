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

public class SuppressionCommentFilterTest
    extends BaseCheckTestSupport {
    private static String[] sAllMessages = {
        "13:17: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "16:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "19:17: Name 'K' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "22:17: Name 'L' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "23:30: Name 'm' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "27:17: Name 'M2' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "28:30: Name 'n' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "32:17: Name 'P' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "35:17: Name 'Q' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "38:17: Name 'R' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "39:30: Name 's' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        "43:17: Name 'T' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        "64:23: Catching 'Exception' is not allowed.",
        "71:11: Catching 'Exception' is not allowed.",
        "77:11: Catching 'RuntimeException' is not allowed.",
        "78:11: Catching 'Exception' is not allowed.",
        "86:31: Catching 'Exception' is not allowed.",
    };

    @Test
    public void testNone() throws Exception {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    //Suppress all checks between default comments
    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        final String[] suppressed = {
            "16:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "43:17: Name 'T' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "64:23: Catching 'Exception' is not allowed.",
            "71:11: Catching 'Exception' is not allowed.",
            "86:31: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckC() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkC", "false");
        final String[] suppressed = {
            "43:17: Name 'T' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "64:23: Catching 'Exception' is not allowed.",
            "71:11: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testCheckCPP() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkCPP", "false");
        final String[] suppressed = {
            "16:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "86:31: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    //Suppress all checks between CS_OFF and CS_ON
    @Test
    public void testOffFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CS_OFF");
        filterConfig.addAttribute("onCommentFormat", "CS_ON");
        final String[] suppressed = {
            "32:17: Name 'P' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "38:17: Name 'R' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "39:30: Name 's' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "42:17: Name 'T' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    //Test suppression of checks of only one type
    //Suppress only ConstantNameCheck between CS_OFF and CS_ON
    @Test
    public void testOffFormatCheck() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CS_OFF");
        filterConfig.addAttribute("onCommentFormat", "CS_ON");
        filterConfig.addAttribute("checkFormat", "ConstantNameCheck");
        final String[] suppressed = {
            "39:30: Name 's' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testArgumentSuppression() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "IllegalCatchCheck OFF\\: (\\w+)");
        filterConfig.addAttribute("onCommentFormat", "IllegalCatchCheck ON\\: (\\w+)");
        filterConfig.addAttribute("checkFormat", "IllegalCatchCheck");
        filterConfig.addAttribute("messageFormat", "^Catching '$1' is not allowed.*$");
        final String[] suppressed = {
            "78:11: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testExpansion() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("offCommentFormat", "CSOFF\\: ([\\w\\|]+)");
        filterConfig.addAttribute("onCommentFormat", "CSON\\: ([\\w\\|]+)");
        filterConfig.addAttribute("checkFormat", "$1");
        final String[] suppressed = {
            "22:17: Name 'L' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:30: Name 'm' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "28:30: Name 'n' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testMessage() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("onCommentFormat", "UNUSED ON\\: (\\w+)");
        filterConfig.addAttribute("offCommentFormat", "UNUSED OFF\\: (\\w+)");
        filterConfig.addAttribute("checkFormat", "Unused");
        filterConfig.addAttribute("messageFormat", "^Unused \\w+ '$1'.$");
        final String[] suppressed = {
            "47:34: Unused parameter 'aInt'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    public static DefaultConfiguration createFilterConfig(Class<?> aClass) {
        return new DefaultConfiguration(aClass.getName());
    }

    protected void verifySuppressed(Configuration aFilterConfig,
                                    String[] aSuppressed)
        throws Exception {
        verify(createChecker(aFilterConfig),
               getPath("filters/InputSuppressionCommentFilter.java"),
               removeSuppressed(sAllMessages, aSuppressed));
    }

    @Override
    protected Checker createChecker(Configuration aFilterConfig)
            throws CheckstyleException, UnsupportedEncodingException {
        final DefaultConfiguration checkerConfig =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createCheckConfig(TreeWalker.class);
        checksConfig.addChild(createCheckConfig(FileContentsHolder.class));
        checksConfig.addChild(createCheckConfig(MemberNameCheck.class));
        checksConfig.addChild(createCheckConfig(ConstantNameCheck.class));
        checksConfig.addChild(createCheckConfig(IllegalCatchCheck.class));
        checkerConfig.addChild(checksConfig);
        if (aFilterConfig != null) {
            checkerConfig.addChild(aFilterConfig);
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

    @Test
    public void testEqualsAndHashCodeOfTagClass() {
        EqualsVerifier.forClass(SuppressionCommentFilter.Tag.class).usingGetClass().verify();
    }

    @Test
    public void testToStringOfTagClass() {
        SuppressionCommentFilter.Tag tag = new SuppressionCommentFilter.Tag(
                0, 1, "text", false, new SuppressionCommentFilter()
        );

        assertEquals("Tag[line=0; col=1; on=false; text='text']", tag.toString());
    }

    @Test(expected = ConversionException.class)
    public void testInvalidCheckFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkFormat", "e[l");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test(expected = ConversionException.class)
    public void testInvalidMessageFormat() throws Exception {
        final DefaultConfiguration filterConfig =
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("messageFormat", "e[l");
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    @Test
    public void testAcceptNullLocalizedMessage() {
        final SuppressionCommentFilter filter = new SuppressionCommentFilter();
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
        SuppressionCommentFilter filter = new SuppressionCommentFilter();
        Assert.assertTrue(filter.accept(auditEvent));
    }
}
