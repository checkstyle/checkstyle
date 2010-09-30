package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class MemberNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^m[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "35:17: Name 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            "224:17: Name 'someMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "56:25: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "8:16: Name '_public' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "9:19: Name '_protected' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "10:9: Name '_package' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "11:17: Name '_private' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testUnderlined() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        final String[] expected = {
            "3:16: Name 'mPublic' must match pattern '^_[a-z]*$'.",
            "4:19: Name 'mProtected' must match pattern '^_[a-z]*$'.",
            "5:9: Name 'mPackage' must match pattern '^_[a-z]*$'.",
            "6:17: Name 'mPrivate' must match pattern '^_[a-z]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testPublicOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        checkConfig.addAttribute("applyToPrivate", "false");
        final String[] expected = {
            "3:16: Name 'mPublic' must match pattern '^_[a-z]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testProtectedOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        checkConfig.addAttribute("applyToPrivate", "false");
        final String[] expected = {
            "4:19: Name 'mProtected' must match pattern '^_[a-z]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testPackageOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPrivate", "false");
        final String[] expected = {
            "5:9: Name 'mPackage' must match pattern '^_[a-z]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testPrivateOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        final String[] expected = {
            "6:17: Name 'mPrivate' must match pattern '^_[a-z]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void testNotPrivate() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("applyToPrivate", "false");
        final String[] expected = {
            "8:16: Name '_public' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "9:19: Name '_protected' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "10:9: Name '_package' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

}

