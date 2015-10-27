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

package com.puppycrawl.tools.checkstyle.grammars;

import java.io.File;
import java.io.IOException;

import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Assume;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

/**
 * Tests GeneratedJava14Lexer.
 * @author Rick Giles
 */
public class GeneratedJava14LexerTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("grammars" + File.separator + filename);
    }

    @Test
    public void testUnexpectedChar() throws Exception {
        // Encoding problems can occur in Windows
        Assume.assumeFalse(SystemUtils.IS_OS_WINDOWS);

        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        // input is 'ÃЯ'
        final String[] expected = {
            "7:9: Name '" + (char) 0xC3 + (char) 0x042F
                 + "' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputGrammar.java"), expected);
    }

    @Test
    public void testSemicolonBetweenImports() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputSemicolonBetweenImports.java"), expected);
    }
}
