package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

/**
 * Tests varargs can be parsed.
 * @author Michael Studman
 */
public class VarargTest
    extends BaseCheckTestCase
{
    public void testCanParse()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("grammars/InputVararg.java"), expected);
    }
}
