package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class HiddenFieldCheckTest
    extends BaseCheckTestCase
{
    public void testNoParameters()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }
    
    /** tests ignoreFormat property */
    public void testIgnoreFormat()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreFormat", "^i.*$");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreSetter property */
    public void testIgnoreSetter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreSetter", "true");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:33: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }

    /** tests ignoreConstructorParameter property */
    public void testIgnoreConstructorParameter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("ignoreConstructorParameter", "true");
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "82:13: 'hidden' hides a field.",
            "100:29: 'prop' hides a field.",
            "106:29: 'prop' hides a field.",
            "112:29: 'prop' hides a field.",
            "124:28: 'prop' hides a field.",
            "138:13: 'hidden' hides a field.",
            "143:13: 'hidden' hides a field.",
            "148:13: 'hidden' hides a field.",
            "152:13: 'hidden' hides a field.",
            "179:23: 'y' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenField.java"), expected);
    }
       
    /** Test against a class with field declarations in different order */
    public void testReordered()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenFieldCheck.class);
        final String[] expected = {
            "18:13: 'hidden' hides a field.",
            "21:40: 'hidden' hides a field.",
            "27:13: 'hidden' hides a field.",
            "32:18: 'hidden' hides a field.",
            "36:33: 'hidden' hides a field.",
            "46:17: 'innerHidden' hides a field.",
            "49:26: 'innerHidden' hides a field.",
            "55:17: 'innerHidden' hides a field.",
            "56:17: 'hidden' hides a field.",
            "61:22: 'innerHidden' hides a field.",
            "64:22: 'hidden' hides a field.",
            "69:17: 'innerHidden' hides a field.",
            "70:17: 'hidden' hides a field.",
            "76:17: 'innerHidden' hides a field.",
            "77:17: 'hidden' hides a field.",
            "83:13: 'hidden' hides a field.",
        };
        verify(checkConfig, getPath("InputHiddenFieldReorder.java"), expected);
    }
}

