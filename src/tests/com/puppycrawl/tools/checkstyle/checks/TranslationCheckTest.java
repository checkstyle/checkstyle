package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class TranslationCheckTest
    extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public void testTranslation()
         throws Exception
    {
        final Configuration checkConfig = createCheckConfig(TranslationCheck.class);
        final String[] expected = {
            "0: Key 'only.english' missing."
        };
        final File[] propertyFiles = new File[] {
            new File(getPath("messages_de.properties")),
            new File(getPath("messages.properties"))
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("messages_de.properties"),
            expected);
    }

    // TODO: test with the same resourcebundle name in different packages
    // x/messages.properties
    //     key1=x
    // y/messages.properties
    //     key2=y
    // should not result in error message about key1 missing in the y bundle

}
