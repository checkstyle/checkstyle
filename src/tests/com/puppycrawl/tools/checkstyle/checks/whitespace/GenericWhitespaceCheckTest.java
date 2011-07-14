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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class GenericWhitespaceCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(GenericWhitespaceCheck.class);
        Map<Class<?>, Integer> x = Maps.newHashMap();
        for (final Map.Entry<Class<?>, Integer> entry : x.entrySet()) {
            entry.getValue();
        }
        //for (final Entry<Class<?>, Integer> entry : entrySet())
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "16:13: '<' is preceded with whitespace.",
            "16:15: '<' is followed by whitespace.",
            "16:23: '>' is preceded with whitespace.",
            "16:43: '<' is preceded with whitespace.",
            "16:45: '<' is followed by whitespace.",
            "16:53: '>' is preceded with whitespace.",
            "17:13: '<' is preceded with whitespace.",
            "17:15: '<' is followed by whitespace.",
            "17:20: '<' is preceded with whitespace.",
            "17:22: '<' is followed by whitespace.",
            "17:30: '>' is preceded with whitespace.",
            "17:32: '>' is followed by whitespace.",
            "17:32: '>' is preceded with whitespace.",
            "17:52: '<' is preceded with whitespace.",
            "17:54: '<' is followed by whitespace.",
            "17:59: '<' is preceded with whitespace.",
            "17:61: '<' is followed by whitespace.",
            "17:69: '>' is preceded with whitespace.",
            "17:71: '>' is followed by whitespace.",
            "17:71: '>' is preceded with whitespace.",
            "30:17: '<' is not preceded with whitespace.",
            "30:21: '>' is followed by an illegal character.",
            "42:21: '<' is preceded with whitespace.",
            "42:30: '>' is followed by whitespace.",
        };
        verify(mCheckConfig,
                getPath("whitespace/InputGenericWhitespaceCheck.java"),
                expected);
    }
}
