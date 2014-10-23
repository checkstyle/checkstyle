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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class JavadocParagraphCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(JavadocParagraphCheck.class);
    }

    @Test
    public void testCorrect() throws Exception
    {
        final String[] expected = {};

        verify(mCheckConfig, getPath("javadoc/InputCorrectJavaDocParagraphCheck.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception
    {
        final String[] expected = {
            "7: <p> tag should be precede with empty line.",
            "8: <p> tag should be precede with empty line.",
            "14: <p> tag should be precede with empty line.",
            "23: <p> tag should be precede with empty line.",
            "32: <p> tag should be precede with empty line.",
            "32: Redundant <p> tag.",
            "33: <p> tag should be precede with empty line.",
            "34: <p> tag should be precede with empty line.",
            "35: <p> tag should be precede with empty line.",
            "39: <p> tag should be precede with empty line.",
            "45: Redundant <p> tag.",
            "50: <p> tag should be precede with empty line.",
            "51: <p> tag should be precede with empty line.",
            "61: Redundant <p> tag.",
            "62: Empty line should be followed by <p> tag on the next line.",
            "70: <p> tag should be precede with empty line.",
            "75: <p> tag should be precede with empty line.",
        };
        verify(mCheckConfig, getPath("javadoc/InputIncorrectJavaDocParagraphCheck.java"), expected);
    }
}
