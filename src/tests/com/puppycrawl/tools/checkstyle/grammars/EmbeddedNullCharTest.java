package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

/**
 * Tests that embedded nulls in string literals does not halt parsing.
 * @author Michael Studman
 */
public class EmbeddedNullCharTest
    extends BaseCheckTestCase
{
    public void testCanParse()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("grammars/InputEmbeddedNullChar.java"), expected);
    }
}
