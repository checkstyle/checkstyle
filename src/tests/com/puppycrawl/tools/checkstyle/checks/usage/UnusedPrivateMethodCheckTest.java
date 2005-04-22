package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UnusedPrivateMethodCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedPrivateMethodCheck.class);
        final String[] expected = {
            "7:18: Unused private method 'methodUnused0'.",
            "66:18: Unused private method 'writeObject'.",
            "71:18: Unused private method 'readObject'.",
            "76:20: Unused private method 'writeReplace'.",
            "82:20: Unused private method 'readResolve'.",
            "91:18: Unused private method 'writeObject'.",
            "95:18: Unused private method 'writeObject'.",
            "99:18: Unused private method 'writeObject'.",
            "103:18: Unused private method 'readObject'.",
            "107:18: Unused private method 'readObject'.",
            "111:17: Unused private method 'writeReplace'.",
            "116:20: Unused private method 'writeReplace'.",
            "121:17: Unused private method 'readResolve'.",
            "126:20: Unused private method 'readResolve'.",
            "134:17: Unused private method 'writeObject'.",
            "139:18: Unused private method 'readObject'.",
            "143:20: Unused private method 'readResolve'.",
            "151:17: Unused private method 'readObject'.",
        };
        verify(checkConfig, getPath("usage/InputUnusedMethod.java"), expected);
    }

    public void testAllowSerializationMethods() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedPrivateMethodCheck.class);
        checkConfig.addAttribute("allowSerializationMethods", "true");

        final String[] expected = {
            "7:18: Unused private method 'methodUnused0'.",
            "91:18: Unused private method 'writeObject'.",
            "95:18: Unused private method 'writeObject'.",
            "99:18: Unused private method 'writeObject'.",
            "103:18: Unused private method 'readObject'.",
            "107:18: Unused private method 'readObject'.",
            "111:17: Unused private method 'writeReplace'.",
            "116:20: Unused private method 'writeReplace'.",
            "121:17: Unused private method 'readResolve'.",
            "126:20: Unused private method 'readResolve'.",
            "134:17: Unused private method 'writeObject'.",
            "139:18: Unused private method 'readObject'.",
            "143:20: Unused private method 'readResolve'.",
            "151:17: Unused private method 'readObject'.",
        };
        verify(checkConfig, getPath("usage/InputUnusedMethod.java"), expected);
    }

   public void testInner() throws Exception
   {
       final DefaultConfiguration checkConfig =
           createCheckConfig(UnusedPrivateMethodCheck.class);
       final String[] expected = {
       };
       verify(checkConfig, getPath("usage/InputInnerUsedMethod.java"), expected);
   }

   public void testEmptyFile() throws Exception
   {
       final DefaultConfiguration checkConfig =
           createCheckConfig(UnusedPrivateMethodCheck.class);
       final String[] expected = {
       };
       verify(checkConfig, getPath("usage/InputEmptyFile.java"), expected);
   }
}
