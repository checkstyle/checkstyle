package com.puppycrawl.tools.checkstyle.grammars;

import java.io.IOException;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

/**
 * Tests GeneratedJava14Lexer.
 * @author Rick Giles
 */
public class GeneratedJava14LexerTest
    extends BaseCheckTestCase
{
    public void testUnexpectedChar() throws IOException, Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "8:10: Got an exception - unexpected char: 0xA9",
        };
//         verify(checkConfig, getPath("grammars/InputGrammar.java"), expected);
    }
}
