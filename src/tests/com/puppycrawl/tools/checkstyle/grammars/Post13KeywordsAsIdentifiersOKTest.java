package com.puppycrawl.tools.checkstyle.grammars;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

import java.io.IOException;

/**
 * @author Michael Studman
 * @see
 */
public class Post13KeywordsAsIdentifiersOKTest
    extends BaseCheckTestCase
{
    public void testUnexpectedChar() throws IOException, Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        verify(checkConfig, getPath("grammars/Post13KeywordsAsIdentifiersOK.java"), new String[0]);
    }
}
