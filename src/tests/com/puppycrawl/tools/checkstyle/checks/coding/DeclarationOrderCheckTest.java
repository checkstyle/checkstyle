package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DeclarationOrderCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        
        final String[] expected = {
            "8:5: Variable access definition in wrong order.",
            "13:5: Variable access definition in wrong order.",
            "18:5: Variable access definition in wrong order.",
            "21:5: Variable access definition in wrong order.",
            "27:5: Static variable definition in wrong order.",
            "27:5: Variable access definition in wrong order.",
            "34:9: Variable access definition in wrong order.",
            "45:9: Static variable definition in wrong order.",
            "45:9: Variable access definition in wrong order.",
            "54:5: Constructor definition in wrong order.",
            "80:5: Instance variable definition in wrong order.",

            "92:9: Variable access definition in wrong order.",
            "100:9: Static variable definition in wrong order.",
            "100:9: Variable access definition in wrong order.",
            "106:5: Variable access definition in wrong order.",
            "111:5: Variable access definition in wrong order.",
            "116:5: Variable access definition in wrong order.",
            "119:5: Variable access definition in wrong order.",
            "125:5: Static variable definition in wrong order.",
            "125:5: Variable access definition in wrong order.",
            "132:9: Variable access definition in wrong order.",
            "143:9: Static variable definition in wrong order.",
            "143:9: Variable access definition in wrong order.",
            "152:5: Constructor definition in wrong order.",
            "178:5: Instance variable definition in wrong order.",
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }
}
