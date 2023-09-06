///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Tests GeneratedJava14Lexer.
 */
public class GeneratedJava14LexerTest
    extends AbstractModuleTestSupport {

    /**
     * Is {@code true} if current default encoding is UTF-8.
     */
    private static final boolean IS_UTF8 = Charset.forName(System.getProperty("file.encoding"))
            .equals(StandardCharsets.UTF_8);

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar";
    }

    @Test
    public void testUnexpectedChar() throws Exception {
        // Encoding problems will occur if default encoding is not UTF-8
        Assumptions.assumeTrue(IS_UTF8, "Problems with encoding may occur");

        // input is 'ÃЯ'
        final String[] expected = {
            "18:9: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    new String(new char[] {0xC3, 0x042F}), "^[a-z][a-zA-Z0-9]*$"),
        };
        verifyWithInlineConfigParser(getPath("InputGrammar.java"), expected);
    }

    @Test
    public void testSemicolonBetweenImports() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSemicolonBetweenImports.java"), expected);
    }

}
