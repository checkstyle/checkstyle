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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HideUtilityClassConstructorCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "design" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final HideUtilityClassConstructorCheck checkObj =
            new HideUtilityClassConstructorCheck();
        final int[] expected = {TokenTypes.CLASS_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testUtilClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUtilityClassConstructor.java"), expected);
    }

    @Test
    public void testUtilClassPublicCtor() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUtilityClassConstructorPublic.java"), expected);
    }

    @Test
    public void testUtilClassPrivateCtor() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUtilityClassConstructorPrivate.java"), expected);
    }

    /** Non-static methods - always OK. */
    @Test
    public void testNonUtilClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputDesignForExtension.java"), expected);
    }

    @Test
    public void testDerivedNonUtilClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNonUtilityClass.java"), expected);
    }

    @Test
    public void testOnlyNonStaticFieldNonUtilClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegression1762702.java"), expected);
    }

    @Test
    public void testEmptyAbstractClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHideUtilityClassConstructor3041574_1.java"), expected);
    }

    @Test
    public void testEmptyClassWithOnlyPrivateFields() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHideUtilityClassConstructor3041574_2.java"), expected);
    }

    @Test
    public void testClassWithStaticInnerClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHideUtilityClassConstructor3041574_3.java"), expected);
    }

    @Test
    public void testProtectedCtor() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HideUtilityClassConstructorCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputHideUtilityClassConstructor.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final HideUtilityClassConstructorCheck obj = new HideUtilityClassConstructorCheck();
        final int[] expected = {TokenTypes.CLASS_DEF};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }
}
