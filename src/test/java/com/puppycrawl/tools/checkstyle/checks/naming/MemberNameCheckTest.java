////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
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
            "10:16: Name '_public' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "11:19: Name '_protected' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "12:9: Name '_package' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "13:17: Name '_private' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
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
            "5:16: Name 'mPublic' must match pattern '^_[a-z]*$'.",
            "6:19: Name 'mProtected' must match pattern '^_[a-z]*$'.",
            "7:9: Name 'mPackage' must match pattern '^_[a-z]*$'.",
            "8:17: Name 'mPrivate' must match pattern '^_[a-z]*$'.",
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
            "5:16: Name 'mPublic' must match pattern '^_[a-z]*$'.",
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
            "6:19: Name 'mProtected' must match pattern '^_[a-z]*$'.",
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
            "7:9: Name 'mPackage' must match pattern '^_[a-z]*$'.",
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
            "8:17: Name 'mPrivate' must match pattern '^_[a-z]*$'.",
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
            "10:16: Name '_public' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "11:19: Name '_protected' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "12:9: Name '_package' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator + "InputMemberName.java"),
               expected);
    }

    @Test
    public void memberNameExtended() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^[a-z][a-z0-9][a-zA-Z0-9]*$");
        final String[] expected = {
            "8:16: Name 'mPublic' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "9:19: Name 'mProtected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "10:9: Name 'mPackage' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "11:17: Name 'mPrivate' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "13:16: Name '_public' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "14:19: Name '_protected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "15:9: Name '_package' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "16:17: Name '_private' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "19:20: Name 'mPublic' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "20:23: Name 'mProtected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "21:13: Name 'mPackage' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "22:21: Name 'mPrivate' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "24:20: Name '_public' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "25:23: Name '_protected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "26:13: Name '_package' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "27:21: Name '_private' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "31:20: Name 'mPublic' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "32:23: Name 'mProtected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "33:13: Name 'mPackage' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "34:21: Name 'mPrivate' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "36:20: Name '_public' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "37:23: Name '_protected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "38:13: Name '_package' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "39:21: Name '_private' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "63:16: Name 'mPublic' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "64:9: Name 'mProtected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "65:9: Name 'mPackage' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "66:9: Name 'mPrivate' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "68:16: Name '_public' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "69:9: Name '_protected' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "70:9: Name '_package' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
            "71:9: Name '_private' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig,
              getPath("naming" + File.separator + "InputMemberNameExtended.java"),
              expected);
    }
}

