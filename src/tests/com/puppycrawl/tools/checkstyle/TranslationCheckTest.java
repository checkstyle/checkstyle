package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;

public class TranslationCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testTranslation()
         throws Exception
    {
        Configuration checkConfig = createCheckConfig(TranslationCheck.class);
        Checker c = createChecker(checkConfig);
        final String bundlePath = getPath("messages_de.properties");
        final String filepath = getPath("InputScopeAnonInner.java");

        final String[] expected = {
            "0: Key 'only.english' missing."
        };
        verify(c, filepath, bundlePath, expected);
    }

    // TODO: test with the same resourcebundle name in different packages
    // x/messages.properties
    //     key1=x
    // y/messages.properties
    //     key2=y
    // should not result in error message about key1 missing in the y bundle

}
