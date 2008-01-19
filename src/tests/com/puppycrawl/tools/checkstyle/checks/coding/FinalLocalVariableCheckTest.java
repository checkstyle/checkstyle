package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class FinalLocalVariableCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);

        final String[] expected = {
            "9:13: Variable 'i' should be declared final.",
            "9:16: Variable 'j' should be declared final.",
            "10:18: Variable 'runnable' should be declared final.",
            "20:13: Variable 'i' should be declared final.",
            "24:13: Variable 'z' should be declared final.",
            "26:16: Variable 'obj' should be declared final.",
            "30:16: Variable 'x' should be declared final.",
            "36:18: Variable 'runnable' should be declared final.",
            "40:21: Variable 'q' should be declared final.",
            "56:13: Variable 'i' should be declared final.",
            "60:13: Variable 'z' should be declared final.",
            "62:16: Variable 'obj' should be declared final.",
            "66:16: Variable 'x' should be declared final.",
            "74:21: Variable 'w' should be declared final.",
            "75:26: Variable 'runnable' should be declared final.",
            "88:18: Variable 'i' should be declared final.",
            "96:17: Variable 'weird' should be declared final.",
            "97:17: Variable 'j' should be declared final.",
            "98:17: Variable 'k' should be declared final.",
        };
        verify(checkConfig, getPath("coding/InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testParameter() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");

        final String[] expected = {
            "45:28: Variable 'aArg' should be declared final.",
            "149:36: Variable '_o' should be declared final.",
            "154:37: Variable '_o1' should be declared final.",
        };
        verify(checkConfig, getPath("coding/InputFinalLocalVariable.java"), expected);
    }
}
