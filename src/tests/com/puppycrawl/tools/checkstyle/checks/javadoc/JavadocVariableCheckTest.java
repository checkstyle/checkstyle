////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import java.io.File;
import org.junit.Test;

public class JavadocVariableCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:5: Missing a Javadoc comment.",
            "304:5: Missing a Javadoc comment.",
            "311:5: Missing a Javadoc comment.",
            "330:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getSrcPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testAnother()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "17:9: Missing a Javadoc comment.",
            "24:9: Missing a Javadoc comment.",
            "30:13: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testAnother2()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testAnother3()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:9: Missing a Javadoc comment.",
            "16:13: Missing a Javadoc comment.",
            "36:9: Missing a Javadoc comment.",
            "43:5: Missing a Javadoc comment.",
            "44:5: Missing a Javadoc comment.",
            "45:5: Missing a Javadoc comment.",
            "46:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }
    @Test
    public void testAnother4()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "46:5: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testScopes() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "3:5: Missing a Javadoc comment.",
            "4:5: Missing a Javadoc comment.",
            "5:5: Missing a Javadoc comment.",
            "6:5: Missing a Javadoc comment.",
            "14:9: Missing a Javadoc comment.",
            "15:9: Missing a Javadoc comment.",
            "16:9: Missing a Javadoc comment.",
            "17:9: Missing a Javadoc comment.",
            "26:9: Missing a Javadoc comment.",
            "27:9: Missing a Javadoc comment.",
            "28:9: Missing a Javadoc comment.",
            "29:9: Missing a Javadoc comment.",
            "38:9: Missing a Javadoc comment.",
            "39:9: Missing a Javadoc comment.",
            "40:9: Missing a Javadoc comment.",
            "41:9: Missing a Javadoc comment.",
            "51:5: Missing a Javadoc comment.",
            "52:5: Missing a Javadoc comment.",
            "53:5: Missing a Javadoc comment.",
            "54:5: Missing a Javadoc comment.",
            "62:9: Missing a Javadoc comment.",
            "63:9: Missing a Javadoc comment.",
            "64:9: Missing a Javadoc comment.",
            "65:9: Missing a Javadoc comment.",
            "74:9: Missing a Javadoc comment.",
            "75:9: Missing a Javadoc comment.",
            "76:9: Missing a Javadoc comment.",
            "77:9: Missing a Javadoc comment.",
            "86:9: Missing a Javadoc comment.",
            "87:9: Missing a Javadoc comment.",
            "88:9: Missing a Javadoc comment.",
            "89:9: Missing a Javadoc comment.",
            "98:9: Missing a Javadoc comment.",
            "99:9: Missing a Javadoc comment.",
            "100:9: Missing a Javadoc comment.",
            "101:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "3:5: Missing a Javadoc comment.",
            "4:5: Missing a Javadoc comment.",
            "14:9: Missing a Javadoc comment.",
            "15:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "5:5: Missing a Javadoc comment.",
            "6:5: Missing a Javadoc comment.",
            "16:9: Missing a Javadoc comment.",
            "17:9: Missing a Javadoc comment.",
            "26:9: Missing a Javadoc comment.",
            "27:9: Missing a Javadoc comment.",
            "28:9: Missing a Javadoc comment.",
            "29:9: Missing a Javadoc comment.",
            "38:9: Missing a Javadoc comment.",
            "39:9: Missing a Javadoc comment.",
            "40:9: Missing a Javadoc comment.",
            "41:9: Missing a Javadoc comment.",
            "51:5: Missing a Javadoc comment.",
            "52:5: Missing a Javadoc comment.",
            "53:5: Missing a Javadoc comment.",
            "54:5: Missing a Javadoc comment.",
            "62:9: Missing a Javadoc comment.",
            "63:9: Missing a Javadoc comment.",
            "64:9: Missing a Javadoc comment.",
            "65:9: Missing a Javadoc comment.",
            "74:9: Missing a Javadoc comment.",
            "75:9: Missing a Javadoc comment.",
            "76:9: Missing a Javadoc comment.",
            "77:9: Missing a Javadoc comment.",
            "86:9: Missing a Javadoc comment.",
            "87:9: Missing a Javadoc comment.",
            "88:9: Missing a Javadoc comment.",
            "89:9: Missing a Javadoc comment.",
            "98:9: Missing a Javadoc comment.",
            "99:9: Missing a Javadoc comment.",
            "100:9: Missing a Javadoc comment.",
            "101:9: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }
}
