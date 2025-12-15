///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.TextBlockGoogleStyleFormattingCheck;

public class XpathRegressionTextBlockGoogleStyleFormattingTest extends AbstractXpathTestSupport {

    private static final Class<TextBlockGoogleStyleFormattingCheck> CLASS =
            TextBlockGoogleStyleFormattingCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Test
    public void testTextBlocksFormatNotVerticallyAligned() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathTextBlockGoogleStyleFormatting.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "8:13: " + getCheckMessage(CLASS,
                TextBlockGoogleStyleFormattingCheck.MSG_VERTICALLY_UNALIGNED),
        };

        final List<String> expectedXpathQueries = List.of(
               "/COMPILATION_UNIT/CLASS_DEF"
                       + "[./IDENT[@text='InputXpathTextBlockGoogleStyleFormatting']]"
                       + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='textFun']]"
                       + "/SLIST/VARIABLE_DEF[./IDENT[@text='simpleScript']]/ASSIGN/EXPR"
                       + "/TEXT_BLOCK_LITERAL_BEGIN[./TEXT_BLOCK_CONTENT"
                       + "[@text=' \\n                s\\n            ']]/TEXT_BLOCK_LITERAL_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTextBlocksFormatNotVerticallyAlignedInMethodCall() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathTextBlockGoogleStyleFormatting2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "14:13: " + getCheckMessage(CLASS,
                TextBlockGoogleStyleFormattingCheck.MSG_VERTICALLY_UNALIGNED),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathTextBlockGoogleStyleFormatting2']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='textFun1']]/SLIST/EXPR"
                    + "/METHOD_CALL[./IDENT[@text='getData']]/ELIST/EXPR/"
                    + "TEXT_BLOCK_LITERAL_BEGIN[./TEXT_BLOCK_CONTENT"
                    + "[@text='\\n                    Hello,"
                    + "\\n                    This is a multi-line message"
                    + ".\\n            ']]/TEXT_BLOCK_LITERAL_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTextBlocksFormatClosingQuotesNotOnNewLine() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathTextBlockGoogleStyleFormatting3.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CLASS);

        final String[] expectedViolation = {
            "7:24: " + getCheckMessage(CLASS,
                    TextBlockGoogleStyleFormattingCheck.MSG_TEXT_BLOCK_CONTENT),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                      + "[./IDENT[@text='InputXpathTextBlockGoogleStyleFormatting3']]"
                      + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='textFun1']]/SLIST/VARIABLE_DEF"
                      + "[./IDENT[@text='simpleScriptViolation']]/ASSIGN/EXPR/"
                      + "TEXT_BLOCK_LITERAL_BEGIN[./TEXT_BLOCK_CONTENT"
                      + "[@text='\\n this is sample text']]/TEXT_BLOCK_LITERAL_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
