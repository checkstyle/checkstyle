package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import org.junit.Test;

/**
 * Tests hex floats and doubles can be parsed.
 * @author Michael Studman
 */
public class HexFloatsTest
    extends BaseCheckTestSupport
{
    @Test
    public void testCanParse()
        throws Exception

    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("grammars/InputHexFloat.java"), expected);
    }
}
