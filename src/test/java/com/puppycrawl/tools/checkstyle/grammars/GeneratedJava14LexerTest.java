package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

import java.io.File;
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
            "8:9: Name 'é' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("grammars/InputGrammar.java"), expected);
    }
    
    @Test
    public void testSemicolonBetweenImports() throws Exception
    {
    	final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {

        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                + "grammars/SemicolonBetweenImports.java").getCanonicalPath(),expected);
    }
}
