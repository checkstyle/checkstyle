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

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NonEmptyAtclauseDescriptionCheckTest
        extends BaseCheckTestSupport
{
    @Test
    public void testCheck()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(NonEmptyAtclauseDescriptionCheck.class);
        final String[] expected = {
            //this is a case with description that is sequences of spaces
            "25: At-clause should have a non-empty description.",
            //this is a case with description that is sequences of spaces
            "26: At-clause should have a non-empty description.",
            //this is a case with description that is sequences of spaces
            "27: At-clause should have a non-empty description.",
            //this is a case with description that is sequences of spaces
            "36: At-clause should have a non-empty description.",
            //this is a case with description that is sequences of spaces
            "37: At-clause should have a non-empty description.",
            //this is a case with description that is sequences of spaces
            "38: At-clause should have a non-empty description.",
            "74: At-clause should have a non-empty description.",
            "75: At-clause should have a non-empty description.",
            "76: At-clause should have a non-empty description.",
            "77: At-clause should have a non-empty description.",
            "78: At-clause should have a non-empty description.",
            "79: At-clause should have a non-empty description.",
            "88: At-clause should have a non-empty description.",
            "89: At-clause should have a non-empty description.",
            "90: At-clause should have a non-empty description.",
            "91: At-clause should have a non-empty description.",
            "92: At-clause should have a non-empty description.",
        };
        verify(checkConfig, getPath("javadoc/InputNonEmptyAtclauseDescriptionCheck.java"), expected);
    }
}
