////////////////////////////////////////////////////////////////////////////////
// Test case for checkstyle.
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.FileContentsHolder;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.usage.UnusedParameterCheck;

public class SuppressionCommentFilterTest
    extends BaseCheckTestCase
{
    static String[] sAllMessages = {
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
        "47:34: Unused parameter 'aInt'.",
        "51:34: Unused parameter 'aInt'.",
        "64:23: Catching 'Exception' is not allowed.",
        "71:11: Catching 'Exception' is not allowed.",
    };

    public void testNone()
            throws Exception
    {
        final DefaultConfiguration filterConfig = null;
        final String[] suppressed = {
        };
        verifySuppressed(filterConfig, suppressed);
    }

    //Supress all checks between default comments
    public void testDefault() throws Exception
    {
        final DefaultConfiguration filterConfig = 
            createFilterConfig(SuppressionCommentFilter.class);
        final String[] suppressed = {
            "16:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "43:17: Name 'T' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "64:23: Catching 'Exception' is not allowed.",
            "71:11: Catching 'Exception' is not allowed.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    public void testCheckC() throws Exception
    {
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

    public void testCheckCPP() throws Exception
    {
        final DefaultConfiguration filterConfig = 
            createFilterConfig(SuppressionCommentFilter.class);
        filterConfig.addAttribute("checkCPP", "false");
        final String[] suppressed = {
            "16:17: Name 'J' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verifySuppressed(filterConfig, suppressed);
    }

    //Supress all checks between CS_OFF and CS_ON
    public void testOffFormat() throws Exception
    {
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

    //Test supression of checks of only one type
    //Supress only ConstantNameCheck between CS_OFF and CS_ON 
    public void testOffFormatCheck() throws Exception
    {
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


    public void testExpansion()
            throws Exception
    {
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

    public void testMessage()
            throws Exception
    {
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

    public static DefaultConfiguration createFilterConfig(Class aClass)
    {
        return new DefaultConfiguration(aClass.getName());
    }

    protected void verifySuppressed(Configuration aFilterConfig,
                                    String[] aSuppressed)
            throws Exception
    {
        verify(createChecker(aFilterConfig),
               getPath("filters/InputSuppressionCommentFilter.java"),
               removeSuppressed(sAllMessages, aSuppressed));
    }

    protected Checker createChecker(Configuration aFilterConfig)
            throws CheckstyleException
    {
        final DefaultConfiguration checkerConfig = 
            new DefaultConfiguration("configuration");
        final DefaultConfiguration checksConfig = createCheckConfig(TreeWalker.class);
        checksConfig.addChild(createCheckConfig(FileContentsHolder.class));
        checksConfig.addChild(createCheckConfig(MemberNameCheck.class));
        checksConfig.addChild(createCheckConfig(ConstantNameCheck.class));
        checksConfig.addChild(createCheckConfig(UnusedParameterCheck.class));
        checksConfig.addChild(createCheckConfig(IllegalCatchCheck.class));
        checkerConfig.addChild(checksConfig);
        if (aFilterConfig != null) {
            checkerConfig.addChild(aFilterConfig);
        }
        final Checker checker = new Checker();
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.configure(checkerConfig);
        checker.addListener(new BriefLogger(mStream));
        return checker;
    }

    private String[] removeSuppressed(String[] aFrom, String[] aRemove)
    {
        final Collection coll = new ArrayList(Arrays.asList(aFrom));
        coll.removeAll(Arrays.asList(aRemove));
        return (String[]) coll.toArray(new String[coll.size()]);
    }
}
