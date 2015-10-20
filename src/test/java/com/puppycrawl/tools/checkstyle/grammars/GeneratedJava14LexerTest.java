package com.puppycrawl.tools.checkstyle.grammars;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
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
        Assume.assumeFalse(SystemUtils.IS_OS_WINDOWS); // Encoding problems can occur in Windows
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "7:9: Name 'ÃЯ' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputGrammar.java"), expected);
    }
    
    @Test
    public void testSemicolonBetweenImports() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("SemicolonBetweenImports.java"), expected);
    }
}
