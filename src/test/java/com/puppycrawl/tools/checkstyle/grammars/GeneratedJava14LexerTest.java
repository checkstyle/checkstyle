package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests GeneratedJava14Lexer.
 * @author Rick Giles
 */
public class GeneratedJava14LexerTest
    extends BaseCheckTestSupport
{
    @Test
    public void testUnexpectedChar() throws IOException, Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "8:10: Got an exception - Unexpected character 0xa9 in identifier",
        };
        verify(checkConfig, getPath("grammars/InputGrammar.java"), expected);
    }
}
