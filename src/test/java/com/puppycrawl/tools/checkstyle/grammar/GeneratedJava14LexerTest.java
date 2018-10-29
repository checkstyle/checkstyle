////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammar;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.Assume;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Tests GeneratedJava14Lexer.
 */
public class GeneratedJava14LexerTest
    extends AbstractModuleTestSupport {

    /**
     * <p>Is {@code true} if this is Windows.</p>
     *
     * <p>Adapted from org.apache.commons.lang3.SystemUtils.</p>
     */
    private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar";
    }

    @Test
    public void testUnexpectedChar() throws Exception {
        // Encoding problems can occur in Windows
        Assume.assumeFalse("Problems with encoding may occur", IS_WINDOWS);

        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        // input is 'ÃЯ'
        final String[] expected = {
            "7:9: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    new String(new char[] {0xC3, 0x042F}), "^[a-z][a-zA-Z0-9]*$"),
        };
        verify(checkConfig, getPath("InputGrammar.java"), expected);
    }

    @Test
    public void testSemicolonBetweenImports() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputSemicolonBetweenImports.java"), expected);
    }

}
