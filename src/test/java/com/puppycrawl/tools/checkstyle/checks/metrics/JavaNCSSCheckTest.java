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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_CLASS;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_FILE;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_METHOD;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Test case for the JavaNCSS-Check.
 *
 */
// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class JavaNCSSCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/javancss";
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavaNCSSCheck.class);

        checkConfig.addAttribute("methodMaximum", "0");
        checkConfig.addAttribute("classMaximum", "1");
        checkConfig.addAttribute("fileMaximum", "2");

        final String[] expected = {
            "2:1: " + getCheckMessage(MSG_FILE, 39, 2),
            "9:1: " + getCheckMessage(MSG_CLASS, 22, 1),
            "14:5: " + getCheckMessage(MSG_METHOD, 2, 0),
            "21:5: " + getCheckMessage(MSG_METHOD, 4, 0),
            "30:5: " + getCheckMessage(MSG_METHOD, 12, 0),
            "42:13: " + getCheckMessage(MSG_METHOD, 2, 0),
            "49:5: " + getCheckMessage(MSG_CLASS, 2, 1),
            "56:1: " + getCheckMessage(MSG_CLASS, 10, 1),
            "61:5: " + getCheckMessage(MSG_METHOD, 8, 0),
            "80:1: " + getCheckMessage(MSG_CLASS, 4, 1),
            "81:5: " + getCheckMessage(MSG_METHOD, 1, 0),
            "82:5: " + getCheckMessage(MSG_METHOD, 1, 0),
            "83:5: " + getCheckMessage(MSG_METHOD, 1, 0),
        };

        verify(checkConfig, getPath("InputJavaNCSS.java"), expected);
    }

    @Test
    public void testEqualToMax() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavaNCSSCheck.class);

        checkConfig.addAttribute("methodMaximum", "12");
        checkConfig.addAttribute("classMaximum", "22");
        checkConfig.addAttribute("fileMaximum", "39");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavaNCSS.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavaNCSSCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavaNCSS.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavaNCSSCheck javaNcssCheckObj = new JavaNCSSCheck();
        final int[] actual = javaNcssCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
        };
        assertNotNull(actual, "Acceptable tokens should not be null");
        assertArrayEquals(expected, actual, "Invalid acceptable tokens");
    }

    @Test
    public void testGetRequiredTokens() {
        final JavaNCSSCheck javaNcssCheckObj = new JavaNCSSCheck();
        final int[] actual = javaNcssCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
        };
        assertNotNull(actual, "Required tokens should not be null");
        assertArrayEquals(expected, actual, "Invalid required tokens");
    }

}
