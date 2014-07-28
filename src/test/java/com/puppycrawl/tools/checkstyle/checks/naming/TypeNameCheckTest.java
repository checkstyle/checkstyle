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
package com.puppycrawl.tools.checkstyle.checks.naming;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TypeNameCheckTest
    extends BaseCheckTestSupport
{

    /**
     * Localized error message from @link {@link TypeNameCheck}.
     */
    private final String msg = getCheckMessage(AbstractNameCheck.MSG_INVALID_PATTERN);

    private final String inputFilename;

    public TypeNameCheckTest() throws IOException
    {
        inputFilename = getPath("naming" + File.separator
                + "InputTypeName.java");
    }

    @Test
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("format", "^inputHe");
        final String[] expected = {
        };
        verify(checkConfig, inputFilename, expected);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(TypeNameCheck.class);
        final String[] expected = {
                buildMesssage(3, 7, "inputHeaderClass",
                        TypeNameCheck.DEFAULT_PATTERN),
                buildMesssage(5, 22, "inputHeaderInterface",
                        TypeNameCheck.DEFAULT_PATTERN),
                buildMesssage(7, 17, "inputHeaderEnum",
                        TypeNameCheck.DEFAULT_PATTERN),
                buildMesssage(9, 23, "inputHeaderAnnotation",
                    TypeNameCheck.DEFAULT_PATTERN),
        };
        verify(checkConfig, inputFilename, expected);
    }

    @Test
    public void testClassSpecific()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenTypes.getTokenName(TokenTypes.CLASS_DEF));
        final String[] expected = {
                buildMesssage(3, 7, "inputHeaderClass",
                        TypeNameCheck.DEFAULT_PATTERN),
        };
        verify(checkConfig, inputFilename, expected);
    }

    @Test
    public void testInterfaceSpecific()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenTypes.getTokenName(TokenTypes.INTERFACE_DEF));
        final String[] expected = {
                buildMesssage(5, 22, "inputHeaderInterface",
                        TypeNameCheck.DEFAULT_PATTERN),
        };
        verify(checkConfig, inputFilename, expected);
    }

    @Test
    public void testEnumSpecific()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenTypes.getTokenName(TokenTypes.ENUM_DEF));
        final String[] expected = {
                buildMesssage(7, 17, "inputHeaderEnum",
                        TypeNameCheck.DEFAULT_PATTERN),
        };
        verify(checkConfig, inputFilename, expected);
    }

    @Test
    public void testAnnotationSpecific()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("tokens", TokenTypes.getTokenName(TokenTypes.ANNOTATION_DEF));
        final String[] expected = {
            buildMesssage(9, 23, "inputHeaderAnnotation",
                        TypeNameCheck.DEFAULT_PATTERN),
        };
        verify(checkConfig, inputFilename, expected);
    }

    private String buildMesssage(int lineNumber, int colNumber, String name,
            String pattern)
    {
        return lineNumber + ":" + colNumber + ": "
                + MessageFormat.format(msg, name, pattern);
    }

}
