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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck.DEFAULT_PATTERN;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

public class TypeNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testSpecified()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("format", "^inputHe");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(TypeNameCheck.class);
        final String[] expected = {
            "3:7: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderClass", DEFAULT_PATTERN),
            "5:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderInterface", DEFAULT_PATTERN),
            "7:17: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderEnum", DEFAULT_PATTERN),
            "9:23: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderAnnotation", DEFAULT_PATTERN),
        };
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testClassSpecific()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenUtils.getTokenName(TokenTypes.CLASS_DEF));
        final String[] expected = {
            "3:7: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderClass", DEFAULT_PATTERN),
        };
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testInterfaceSpecific()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenUtils.getTokenName(TokenTypes.INTERFACE_DEF));
        final String[] expected = {
            "5:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderInterface", DEFAULT_PATTERN),
        };
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testEnumSpecific()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenUtils.getTokenName(TokenTypes.ENUM_DEF));
        final String[] expected = {
            "7:17: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderEnum", DEFAULT_PATTERN),
        };
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testAnnotationSpecific()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenUtils.getTokenName(TokenTypes.ANNOTATION_DEF));
        final String[] expected = {
            "9:23: " + getCheckMessage(MSG_INVALID_PATTERN,
                "inputHeaderAnnotation", DEFAULT_PATTERN),
        };
        verify(checkConfig, getPath("InputTypeName.java"), expected);
    }
}
