////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MultipleStringLiteralsCheckTest extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");
        checkConfig.addAttribute("ignoreStringsRegexp", "");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "8:17: " + getCheckMessage(MSG_KEY, "\"\"", 4),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItIgnoreEmpty() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItIgnoreEmptyAndComspace() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");
        checkConfig.addAttribute("ignoreStringsRegexp", "^((\"\")|(\", \"))$");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItWithoutIgnoringAnnotations() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "3");
        checkConfig.addAttribute("ignoreOccurrenceContext", "");

        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_KEY, "\"unchecked\"", 4),
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        MultipleStringLiteralsCheck check = new MultipleStringLiteralsCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

}
