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
            "16:13: 'hidden' hides a field.",
            "25:13: 'hidden' hides a field.",
            "30:18: 'hidden' hides a field.",
            "44:17: 'innerHidden' hides a field.",
            "53:17: 'innerHidden' hides a field.",
            "54:17: 'hidden' hides a field.",
            "59:22: 'innerHidden' hides a field.",
            "62:22: 'hidden' hides a field.",
            "74:17: 'innerHidden' hides a field.",
            "75:17: 'hidden' hides a field.",
            "80:13: 'hidden' hides a field.",
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
            "16:13: 'hidden' hides a field.",
            "19:33: 'hidden' hides a field.",
            "25:13: 'hidden' hides a field.",
            "30:18: 'hidden' hides a field.",
            "34:33: 'hidden' hides a field.",
            "44:17: 'innerHidden' hides a field.",
            "47:26: 'innerHidden' hides a field.",
            "53:17: 'innerHidden' hides a field.",
            "54:17: 'hidden' hides a field.",
            "59:22: 'innerHidden' hides a field.",
            "62:22: 'hidden' hides a field.",
            "67:17: 'innerHidden' hides a field.",
            "68:17: 'hidden' hides a field.",
            "74:17: 'innerHidden' hides a field.",
            "75:17: 'hidden' hides a field.",
            "80:13: 'hidden' hides a field.",
        };
        verify(c, fname, expected);
    }
}

