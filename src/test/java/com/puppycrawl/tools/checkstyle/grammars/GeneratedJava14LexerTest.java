package com.puppycrawl.tools.checkstyle.grammars;

import java.io.File;
import java.io.IOException;

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
    @Test
    public void testUnexpectedChar() throws IOException, Exception {
        Assume.assumeFalse(SystemUtils.IS_OS_WINDOWS); // Encoding problems can occur in Windows
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "7:9: Name 'ÃЯ' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("grammars/InputGrammar.java"), expected);
    }
    
    @Test
    public void testSemicolonBetweenImports() throws IOException, Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {

        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/grammars/SemicolonBetweenImports.java").getCanonicalPath(), expected);
    }
}
