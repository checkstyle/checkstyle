package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ModifierOrderCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "14:10: 'final' modifier out of order with the JLS suggestions.",
            "18:12: 'private' modifier out of order with the JLS suggestions.",
            "24:14: 'private' modifier out of order with the JLS suggestions.",
            "34:13: '@MyAnnotation' annotation modifier does not preceed non-annotation modifiers.",
            "39:13: '@MyAnnotation' annotation modifier does not preceed non-annotation modifiers.",
            "49:34: '@MyAnnotation2' annotation modifier does not preceed non-annotation modifiers.",
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}
