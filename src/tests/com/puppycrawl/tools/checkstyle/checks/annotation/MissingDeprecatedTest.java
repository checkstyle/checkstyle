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

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MissingDeprecatedTest extends BaseCheckTestSupport
{
    /**
     * Tests that memebers that are only deprecated via javadoc are flagged.
     */
    @Test
    public void testBadDeprecatedAnnotation() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "12: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "19: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "26: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "31: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "38: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "43: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "51: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "56: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadDeprecatedAnnotation.java"), expected);
    }

    /**
     * Tests that memebers that are only deprecated via the annotation are flagged.
     */
    @Test
    public void testBadDeprecatedJavadoc() throws Exception
    {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "5: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "11: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "16: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "23: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "29: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "38: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "40: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "48: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "55: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadDeprecatedJavadoc.java"), expected);
    }

    /**
     * Tests various special deprecation conditions such as duplicate or empty tags.
     */
    @Test
    public void testSpecialCaseDeprecated() throws Exception
    {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "5: Duplicate @deprecated tag.",
            "12: Duplicate @deprecated tag.",
            "14: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "17: Missing a Javadoc comment.",
            "19: Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.",
            "24: Missing a Javadoc comment.",
            "32: Missing a Javadoc comment.",
            "33: Duplicate @deprecated tag.",
            "33: Missing a Javadoc comment.",
            "42: Duplicate @deprecated tag.",
            "42: Missing a Javadoc comment.",
            "50: Missing a Javadoc comment.",
            "51: Duplicate @deprecated tag.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "SpecialCaseDeprecated.java"), expected);
    }

    /**
     * Tests that good forms of deprecation are not flagged.
     */
    @Test
    public void testGoodDeprecated() throws Exception
    {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodDeprecated.java"), expected);
    }
}
