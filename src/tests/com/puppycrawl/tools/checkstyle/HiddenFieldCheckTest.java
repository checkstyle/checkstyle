package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.HiddenFieldCheck;

public class HiddenFieldCheckTest
    extends BaseCheckTestCase
{
    public void testNoParameters()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(HiddenFieldCheck.class.getName());
        checkConfig.addProperty("checkParameters", "false");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputHiddenField.java");
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
        };
        verify(c, fname, expected);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(HiddenFieldCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputHiddenField.java");
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
        };
        verify(c, fname, expected);
    }
}

