////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class JavadocStyleCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefaultSettings()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected =
        {
            "20: First sentence should end with a period.",
            "53: First sentence should end with a period.",
            "63:11: Unclosed HTML tag found: <b>This guy is missing end of bold tag",
            "66:7: Extra HTML tag found: </td>Extra tag shouldn't be here",
            "68:19: Unclosed HTML tag found: <code>dummy.",
            "74: First sentence should end with a period.",
            "75:23: Unclosed HTML tag found: <b>should fail",
            "81: First sentence should end with a period.",
            "82:31: Unclosed HTML tag found: <b>should fail",
            "88: First sentence should end with a period.",
            "89:31: Extra HTML tag found: </code>",
            "90: Incomplete HTML tag found:     * should fail <",
            "109:39: Extra HTML tag found: </img>",
            "186:8: Unclosed HTML tag found: <blockquote>",
            "193: First sentence should end with a period.",
            "238: First sentence should end with a period.",
            "335:33: Extra HTML tag found: </string>",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testFirstSentence() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        final String[] expected =
        {
            "20: First sentence should end with a period.",
            "53: First sentence should end with a period.",
            "74: First sentence should end with a period.",
            "81: First sentence should end with a period.",
            "88: First sentence should end with a period.",
            "193: First sentence should end with a period.",
            "238: First sentence should end with a period.",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testFirstSentenceFormat() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("endOfSentenceFormat",
                "([.][ \t\n\r\f<])|([.]$)");
        final String[] expected =
        {
            "20: First sentence should end with a period.",
            "32: First sentence should end with a period.",
            "39: First sentence should end with a period.",
            "53: First sentence should end with a period.",
            "74: First sentence should end with a period.",
            "81: First sentence should end with a period.",
            "88: First sentence should end with a period.",
            "193: First sentence should end with a period.",
            "238: First sentence should end with a period.",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testHtml() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected =
        {
            "63:11: Unclosed HTML tag found: <b>This guy is missing end of bold tag",
            "66:7: Extra HTML tag found: </td>Extra tag shouldn't be here",
            "68:19: Unclosed HTML tag found: <code>dummy.",
            "75:23: Unclosed HTML tag found: <b>should fail",
            "82:31: Unclosed HTML tag found: <b>should fail",
            "89:31: Extra HTML tag found: </code>",
            "90: Incomplete HTML tag found:     * should fail <",
            "109:39: Extra HTML tag found: </img>",
            "186:8: Unclosed HTML tag found: <blockquote>",
            "335:33: Extra HTML tag found: </string>",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testScopePublic()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "public");
        final String[] expected =
        {
            "88: First sentence should end with a period.",
            "89:31: Extra HTML tag found: </code>",
            "90: Incomplete HTML tag found:     * should fail <",
            "205: Javadoc has empty description section.",
            "230: Javadoc has empty description section.",
            "238: First sentence should end with a period.",
            "335:33: Extra HTML tag found: </string>",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testScopeProtected()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "protected");
        final String[] expected =
        {
            "74: First sentence should end with a period.",
            "75:23: Unclosed HTML tag found: <b>should fail",
            "88: First sentence should end with a period.",
            "89:31: Extra HTML tag found: </code>",
            "90: Incomplete HTML tag found:     * should fail <",
            "205: Javadoc has empty description section.",
            "211: Javadoc has empty description section.",
            "230: Javadoc has empty description section.",
            "238: First sentence should end with a period.",
            "335:33: Extra HTML tag found: </string>",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testScopePackage()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "package");
        final String[] expected =
        {
            "74: First sentence should end with a period.",
            "75:23: Unclosed HTML tag found: <b>should fail",
            "81: First sentence should end with a period.",
            "82:31: Unclosed HTML tag found: <b>should fail",
            "88: First sentence should end with a period.",
            "89:31: Extra HTML tag found: </code>",
            "90: Incomplete HTML tag found:     * should fail <",
            "205: Javadoc has empty description section.",
            "211: Javadoc has empty description section.",
            "218: Javadoc has empty description section.",
            "230: Javadoc has empty description section.",
            "238: First sentence should end with a period.",
            "335:33: Extra HTML tag found: </string>",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testEmptyJavadoc() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        final String[] expected =
        {
            "205: Javadoc has empty description section.",
            "211: Javadoc has empty description section.",
            "218: Javadoc has empty description section.",
            "225: Javadoc has empty description section.",
            "230: Javadoc has empty description section.",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void testExcludeScope()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("excludeScope", "protected");
        final String[] expected =
        {
            "20: First sentence should end with a period.",
            "53: First sentence should end with a period.",
            "63:11: Unclosed HTML tag found: <b>This guy is missing end of bold tag",
            "66:7: Extra HTML tag found: </td>Extra tag shouldn't be here",
            "68:19: Unclosed HTML tag found: <code>dummy.",
            "81: First sentence should end with a period.",
            "82:31: Unclosed HTML tag found: <b>should fail",
            "109:39: Extra HTML tag found: </img>",
            "186:8: Unclosed HTML tag found: <blockquote>",
            "193: First sentence should end with a period.",
        };

        verify(checkConfig, getPath("InputJavadocStyleCheck.java"), expected);
    }

    @Test
    public void packageInfoInheritDoc() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected =
        {
            "1: First sentence should end with a period.",
        };

        String basePath = "javadoc" + File.separator
            + "pkginfo" + File.separator + "invalidinherit" + File.separator;

        verify(createChecker(checkConfig),
               getPath(basePath + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoInvalid() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected =
        {
            "1: First sentence should end with a period.",
        };

        String basePath = "javadoc" + File.separator
            + "pkginfo" + File.separator + "invalidformat" + File.separator;

        verify(createChecker(checkConfig),
               getPath(basePath + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoAnnotation() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected =
        {
        };

        String basePath = "javadoc" + File.separator
            + "pkginfo" + File.separator + "annotation" + File.separator;

        verify(createChecker(checkConfig),
               getPath(basePath + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoMissing() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: Missing a Javadoc comment.",
        };

        String basePath = "javadoc" + File.separator
            + "bothfiles" + File.separator;

        verify(createChecker(checkConfig),
               getPath(basePath + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoValid() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {};

        String basePath = "javadoc" + File.separator
            + "pkginfo" + File.separator + "valid" + File.separator;

        verify(createChecker(checkConfig),
               getPath(basePath + "package-info.java"),
               expected);
    }
}
