////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocPackageCheck.MSG_PKG_JAVADOC_MISSING;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocPackageCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocpackage";
    }

    @Test
    public void testPackageJavadocPresent() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageSingleLineJavadocPresent() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("singleline/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithAnnotation() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("annotation/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithBlankLines() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("blank/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissing() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/package-info.java"), expected);
    }

    @Test
    public void testBlockCommentInsteadOfJavadoc() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/blockcomment/package-info.java"), expected);
    }

    @Test
    public void testSinglelineCommentInsteadOfJavadoc() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "2: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/singleline/package-info.java"), expected);
    }

    @Test
    public void testSinglelineCommentInsteadOfJavadoc2() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "2: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/single/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithAnnotation() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "2: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/annotation/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithAnnotationAndBlockComment() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "6: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/annotation/blockcomment/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingDetachedJavadoc() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "5: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/detached/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithHeader() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("header/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithBlankLines() throws Exception {
        final DefaultConfiguration config = createModuleConfig(MissingJavadocPackageCheck.class);
        final String[] expected = {
            "2: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verify(config, getPath("nojavadoc/blank/package-info.java"), expected);
    }

    @Test
    public void testTokensAreCorrect() {
        final MissingJavadocPackageCheck check = new MissingJavadocPackageCheck();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
        };
        assertArrayEquals(expected, check.getAcceptableTokens(),
                "Acceptable required tokens are invalid");
        assertArrayEquals(expected, check.getDefaultTokens(),
                "Default required tokens are invalid");
        assertArrayEquals(expected, check.getRequiredTokens(),
                "Required required tokens are invalid");
    }
}
