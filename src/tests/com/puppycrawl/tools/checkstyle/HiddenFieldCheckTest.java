package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.HiddenFieldCheck;

public class HiddenFieldCheckTest
    extends BaseCheckTestCase
{
    public HiddenFieldCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(HiddenFieldCheck.class.getName());;
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputHiddenField.java");
        final String[] expected = {
            "16:13: 'hidden' hides a field.",
            "25:13: 'hidden' hides a field.",
            "30:18: 'hidden' hides a field.",
            "44:17: 'innerHidden' hides a field.",
            "53:17: 'innerHidden' hides a field.",
            "59:22: 'innerHidden' hides a field.",
            "74:17: 'innerHidden' hides a field.",
            "79:13: 'hidden' hides a field.",
        };
        verify(c, fname, expected);
    }

    public void testParameters()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(HiddenFieldCheck.class.getName());
        checkConfig.addProperty("checkParameters", "true");
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
            "59:22: 'innerHidden' hides a field.",
            "67:17: 'innerHidden' hides a field.",
            "74:17: 'innerHidden' hides a field.",
            "79:13: 'hidden' hides a field.",
        };
        verify(c, fname, expected);
    }
}

