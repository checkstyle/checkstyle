////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.io.File;
import org.junit.Test;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AnnotationUseStyleTest extends BaseCheckTestSupport
{
    /**
     * Test that annotation parens are always present.
     * @throws Exception
     */
    @Test
    public void testParansAlways() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ALWAYS");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "3: Annotation must have closing parenthesis.",
            "18: Annotation must have closing parenthesis.",
            "23: Annotation must have closing parenthesis.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }

    /**
     * Test that annotation parens are never present.
     * @throws Exception
     */
    @Test
    public void testParansNever() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "NEVER");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "13: Annotation cannot have closing parenthesis.",
            "30: Annotation cannot have closing parenthesis.",
            "33: Annotation cannot have closing parenthesis.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleExpanded() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "EXPANDED");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "5: Annotation style must be 'EXPANDED'.",
            "12: Annotation style must be 'EXPANDED'.",
            "20: Annotation style must be 'EXPANDED'.",
            "26: Annotation style must be 'EXPANDED'.",
            "39: Annotation style must be 'EXPANDED'.",
            "41: Annotation style must be 'EXPANDED'.",
            "58: Annotation style must be 'EXPANDED'.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleCompact() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "COMPACT");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "43: Annotation style must be 'COMPACT'.",
            "47: Annotation style must be 'COMPACT'.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }

    @Test
    public void testStyleCompactNoArray() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "COMPACT_NO_ARRAY");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
            "5: Annotation style must be 'COMPACT_NO_ARRAY'.",
            "20: Annotation style must be 'COMPACT_NO_ARRAY'.",
            "41: Annotation style must be 'COMPACT_NO_ARRAY'.",
            "43: Annotation style must be 'COMPACT_NO_ARRAY'.",
            "47: Annotation style must be 'COMPACT_NO_ARRAY'.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }

    @Test
    public void testCommaAlwaysViolations() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ALWAYS");
        final String[] expected = {
            "3:20: Annotation array values must contain trailing comma.",
            "6:30: Annotation array values must contain trailing comma.",
            "10:40: Annotation array values must contain trailing comma.",
            "13:44: Annotation array values must contain trailing comma.",
            "16:54: Annotation array values must contain trailing comma.",
            "24:37: Annotation array values must contain trailing comma.",
            "24:65: Annotation array values must contain trailing comma.",
            "26:21: Annotation array values must contain trailing comma.",
            "26:30: Annotation array values must contain trailing comma.",
            "29:39: Annotation array values must contain trailing comma.",
            "29:49: Annotation array values must contain trailing comma.",
            "32:21: Annotation array values must contain trailing comma.",
            "32:56: Annotation array values must contain trailing comma.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "AnnotationUseNoTrailingComma.java"), expected);
    }

    @Test
    public void testCommaAlwaysNoViolations() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ALWAYS");
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "AnnotationUseWithTrailingComma.java"), expected);
    }

    @Test
    public void testCommaNeverViolations() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "NEVER");
        final String[] expected = {
            "9:32: Annotation array values cannot contain trailing comma.",
            "13:42: Annotation array values cannot contain trailing comma.",
            "16:46: Annotation array values cannot contain trailing comma.",
            "19:56: Annotation array values cannot contain trailing comma.",
            "27:38: Annotation array values cannot contain trailing comma.",
            "27:67: Annotation array values cannot contain trailing comma.",
            "33:39: Annotation array values cannot contain trailing comma.",
            "33:50: Annotation array values cannot contain trailing comma.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "AnnotationUseWithTrailingComma.java"), expected);
    }

    @Test
    public void testCommaNeverNoViolations() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "NEVER");
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "AnnotationUseNoTrailingComma.java"), expected);
    }

    @Test
    public void testEverythingMixed() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationUseStyleCheck.class);
        checkConfig.addAttribute("closingParens", "ignore");
        checkConfig.addAttribute("elementStyle", "ignore");
        checkConfig.addAttribute("trailingArrayComma", "ignore");
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "DifferentUseStyles.java"), expected);
    }
}
