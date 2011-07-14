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

/**
 * @author Oliver.Burn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JavadocTypeCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testTags() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "8: Missing a Javadoc comment.",
            "302: Missing a Javadoc comment.",
            "327: Missing a Javadoc comment.",
        };
        verify(checkConfig, getSrcPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "14: Missing a Javadoc comment.",
            "21: Missing a Javadoc comment.",
            "27: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "9: Missing a Javadoc comment.",
            "14: Missing a Javadoc comment.",
            "34: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testProtected() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testPublic() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testProtest() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected =
        {
            "7: Missing a Javadoc comment.",
            "29: Missing a Javadoc comment.",
            "38: Missing a Javadoc comment.",
            "65: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testPkg() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("package").getName());
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
            "20: Missing a Javadoc comment.",
            "22: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("public").getName());
        final String[] expected =
        {
            "18: Missing a Javadoc comment.",
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected =
        {
            "13: Type Javadoc comment is missing an @author tag.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testAuthorRegularEx()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "0*");
        final String[] expected = {
            "22: Type Javadoc comment is missing an @author tag.",
            "58: Type Javadoc comment is missing an @author tag.",
            "94: Type Javadoc comment is missing an @author tag.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testAuthorRegularExError()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "ABC");
        final String[] expected = {
            "13: Type Javadoc tag @author must match pattern 'ABC'.",
            "22: Type Javadoc comment is missing an @author tag.",
            "31: Type Javadoc tag @author must match pattern 'ABC'.",
            "49: Type Javadoc tag @author must match pattern 'ABC'.",
            "58: Type Javadoc comment is missing an @author tag.",
            "67: Type Javadoc tag @author must match pattern 'ABC'.",
            "85: Type Javadoc tag @author must match pattern 'ABC'.",
            "94: Type Javadoc comment is missing an @author tag.",
            "103: Type Javadoc tag @author must match pattern 'ABC'.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testVersionRequired()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\S");
        final String[] expected = {
            "13: Type Javadoc comment is missing an @version tag.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testVersionRegularEx()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "^\\p{Digit}+\\.\\p{Digit}+$");
        final String[] expected = {
            "22: Type Javadoc comment is missing an @version tag.",
            "58: Type Javadoc comment is missing an @version tag.",
            "94: Type Javadoc comment is missing an @version tag.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testVersionRegularExError()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\$Revision.*\\$");
        final String[] expected = {
            "13: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "22: Type Javadoc comment is missing an @version tag.",
            "31: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "40: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "49: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "58: Type Javadoc comment is missing an @version tag.",
            "67: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "76: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "85: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "94: Type Javadoc comment is missing an @version tag.",
            "103: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
            "112: Type Javadoc tag @version must match pattern '\\$Revision.*\\$'.",
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testScopes() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "1: Missing a Javadoc comment.",
            "13: Missing a Javadoc comment.",
            "25: Missing a Javadoc comment.",
            "37: Missing a Javadoc comment.",
            "50: Missing a Javadoc comment.",
            "61: Missing a Javadoc comment.",
            "73: Missing a Javadoc comment.",
            "85: Missing a Javadoc comment.",
            "97: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "1: Missing a Javadoc comment.",
            "13: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "25: Missing a Javadoc comment.",
            "37: Missing a Javadoc comment.",
            "50: Missing a Javadoc comment.",
            "61: Missing a Javadoc comment.",
            "73: Missing a Javadoc comment.",
            "85: Missing a Javadoc comment.",
            "97: Missing a Javadoc comment.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7:4: Unused @param tag for '<D123>'.",
            "11: Type Javadoc comment is missing an @param <C456> tag.",
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }
    @Test
    public void testAllowMissingTypeParameters() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        final String[] expected = {
            "7:4: Unused @param tag for '<D123>'.",
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void testBadTag() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "3:4: Unknown tag 'mytag'.",
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputBadTag.java"),
               expected);
    }

    @Test
    public void testBadTagSuppress() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowUnknownTags", "true");
        final String[] expected = {
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputBadTag.java"),
               expected);
    }
}
