////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_EQUALS;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck.MSG_KEY_HASHCODE;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class EqualsHashCodeCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "94:13: " + getCheckMessage(MSG_KEY_HASHCODE),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testNoEquals() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY_EQUALS),
        };
        verify(checkConfig, getPath("InputEqualsHashCodeNoEquals.java"), expected);
    }

    @Test
    public void testBooleanMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsHashCodeCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEqualsHashCode.java"), expected);
    }

    @Test
    public void testEqualsParameter() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsHashCodeCheck.class);
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "18:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "48:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "53:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "65:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "68:9: " + getCheckMessage(MSG_KEY_HASHCODE),
            "75:9: " + getCheckMessage(MSG_KEY_EQUALS),
            "82:9: " + getCheckMessage(MSG_KEY_HASHCODE),
        };
        verify(checkConfig, getPath("InputEqualsParameter.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsHashCodeCheck check = new EqualsHashCodeCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
