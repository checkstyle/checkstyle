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
package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import org.junit.Test;

public class MissingOverrideCheckTest extends BaseCheckTestSupport
{
    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testBadOverrideFromObject() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "false");

        final String[] expected = {
            "8: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "30: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "41: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "50: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testBadOverrideFromObjectJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
            "8: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "30: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "41: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "50: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadOverrideFromObject.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testBadOverrideFromOther() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "10: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "26: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "34: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "40: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "47: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "53: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "63: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testBadOverrideFromOtherJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadOverrideFromOther.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testBadAnnonOverride() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "10: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "16: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "29: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
            "35: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadAnnonOverride.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testBadAnnonOverrideJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadAnnonOverride.java"), expected);
    }

    /**
     * Tests that inheritDoc misuse is properly flagged or missing Javadocs do not cause a problem.
     * @throws Exception
     */
    @Test
    public void testNotOverride() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "8: The Javadoc {@inheritDoc} tag is not valid at this location.",
            "15: The Javadoc {@inheritDoc} tag is not valid at this location.",
            //this wont be flagged because this check only checks methods.
            //"22: The Javadoc comment contains an {@inheritDoc} tag. The tag is not valid at this location.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "NotOverride.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testGoodOverrideFromObject() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "false");

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testGoodOverrideFromObjectJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodOverrideFromObject.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testGoodOverrideFromOther() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testGoodOverrideFromOtherJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodOverrideFromOther.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     * @throws Exception
     */
    @Test
    public void testGoodAnnonOverride() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodAnnonOverride.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     * @throws Exception
     */
    @Test
    public void testGoodAnnonOverrideJ5Compat() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodAnnonOverride.java"), expected);
    }
}
