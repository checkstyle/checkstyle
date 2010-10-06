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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

/**
 * @author Rob Worth
 * @author Lars Kühne
 */
public class AnonInnerLengthCheckTest extends BaseCheckTestSupport
{

    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AnonInnerLengthCheck.class);
        final String[] expected = {
            "50:35: Anonymous inner class length is 21 lines (max allowed is 20).",
        };
        verify(checkConfig, getPath("InputAnonInnerLength.java"), expected);
    }

    @Test
    public void testNonDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AnonInnerLengthCheck.class);
        checkConfig.addAttribute("max", "6");
        final String[] expected = {
            "50:35: Anonymous inner class length is 21 lines (max allowed is 6).",
            "75:35: Anonymous inner class length is 20 lines (max allowed is 6).",
        };
        verify(checkConfig, getPath("InputAnonInnerLength.java"), expected);
    }
}
