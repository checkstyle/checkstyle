package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class MethodNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);
        final String[] expected = {
            "137:10: Name 'ALL_UPPERCASE_METHOD' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception
    {
         final DefaultConfiguration checkConfig =
             createCheckConfig(MethodNameCheck.class);

         final String[] expected = {
                 "12:16: Method Name 'InputMethNameEqualClsName' must not equal the enclosing class name.",
                 "12:16: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "18:20: Method Name 'Inner' must not equal the enclosing class name.",
                 "18:20: Name 'Inner' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "23:20: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "32:24: Method Name 'InputMethNameEqualClsName' must not equal the enclosing class name.",
                 "32:24: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "42:9: Method Name 'SweetInterface' must not equal the enclosing class name.",
                 "42:9: Name 'SweetInterface' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "48:17: Method Name 'Outter' must not equal the enclosing class name.",
                 "48:17: Name 'Outter' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
         };

         verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception
    {
         final DefaultConfiguration checkConfig =
             createCheckConfig(MethodNameCheck.class);
         checkConfig.addAttribute("allowClassName", "true"); //allow method names and class names to equal

         final String[] expected = {
                 "12:16: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "18:20: Name 'Inner' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "23:20: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "32:24: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "42:9: Name 'SweetInterface' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
                 "48:17: Name 'Outter' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
         };

         verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception
    {
         final DefaultConfiguration checkConfig =
             createCheckConfig(MethodNameCheck.class);

         final String[] expected = {
         };

         verify(checkConfig, getPath("naming/InputMethodNameExtra.java"), expected);
    }
}
