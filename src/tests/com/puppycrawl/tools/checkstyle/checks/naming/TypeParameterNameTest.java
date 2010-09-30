package com.puppycrawl.tools.checkstyle.checks.naming;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TypeParameterNameTest
    extends BaseCheckTestSupport
{
    @Test
    public void testClassDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        final String[] expected = {
            "3:38: Name 't' must match pattern '^[A-Z]$'.",
            "11:14: Name 'foo' must match pattern '^[A-Z]$'.",
            "25:24: Name 'foo' must match pattern '^[A-Z]$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);
        final String[] expected = {
            "5:13: Name 'TT' must match pattern '^[A-Z]$'.",
            "7:6: Name 'e_e' must match pattern '^[A-Z]$'.",
            "17:6: Name 'Tfo$o2T' must match pattern '^[A-Z]$'.",
            "21:6: Name 'foo' must match pattern '^[A-Z]$'.",
            "26:10: Name '_fo' must match pattern '^[A-Z]$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String[] expected = {
            "3:38: Name 't' must match pattern '^foo$'.",
            "31:18: Name 'T' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodFooName()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String[] expected = {
            "5:13: Name 'TT' must match pattern '^foo$'.",
            "7:6: Name 'e_e' must match pattern '^foo$'.",
            "17:6: Name 'Tfo$o2T' must match pattern '^foo$'.",
            "26:10: Name '_fo' must match pattern '^foo$'.",
            "33:6: Name 'E' must match pattern '^foo$'.",
            "35:14: Name 'T' must match pattern '^foo$'.",
            //"40:14: Name 'EE' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }
}
