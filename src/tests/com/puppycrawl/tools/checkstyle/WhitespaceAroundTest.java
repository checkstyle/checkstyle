package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.checks.WhitespaceAroundCheck;

public class WhitespaceAroundTest
        extends AbstractCheckTestCase
{
    public WhitespaceAroundTest(String aName)
    {
        super(aName);
    }

    public void testIt()
        throws Exception
    {
        final LocalizedMessages msgs = new LocalizedMessages(8);
        final TreeWalker walker = new TreeWalker(msgs);
        final CheckConfiguration config = new CheckConfiguration();
        config.setClassname(WhitespaceAroundCheck.class.getName());
        final Check c = config.createInstance(
            Thread.currentThread().getContextClassLoader());
        walker.registerCheck(c, config);
        final String fname = CheckerTest.getPath("InputWhitespace.java");
        final String[] lines = getLines(fname);
        walker.walk(getAST(fname, lines), lines, fname);
        assertEquals(29, msgs.getMessages().length);
        int idx = 0;
        verifyMessage(msgs, idx++, 16, 22, "'=' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 16, 23, "'=' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 18, 24, "'=' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 26, 14, "'=' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 27, 10, "'=' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 27, 11, "'=' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 28, 10, "'+=' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 28, 12, "'+=' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 29, 13, "'-=' is not followed by whitespace.");
        //verifyMessage(msgs, idx++, 29, 14, "'-' is followed by whitespace.");
        //verifyMessage(msgs, idx++, 29, 21, "'+' is followed by whitespace.");
        //verifyMessage(msgs, idx++, 30, 14, "'++' is preceeded with whitespace.");
        //verifyMessage(msgs, idx++, 30, 21, "'--' is preceeded with whitespace.");
        //verifyMessage(msgs, idx++, 31, 15, "'++' is followed by whitespace.");
        //verifyMessage(msgs, idx++, 31, 22, "'--' is followed by whitespace.");
        verifyMessage(msgs, idx++, 37, 21, "'synchronized' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 39, 12, "'try' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 41, 14, "'catch' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 58, 11, "'if' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 76, 19, "'return' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 97, 29, "'?' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 97, 30, "'?' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 98, 15, "'==' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 98, 17, "'==' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 104, 20, "'*' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 104, 21, "'*' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 119, 18, "'%' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 120, 20, "'%' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 121, 18, "'%' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 121, 19, "'%' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 123, 18, "'/' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 124, 20, "'/' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 125, 18, "'/' is not preceeded with whitespace.");
        verifyMessage(msgs, idx++, 125, 19, "'/' is not followed by whitespace.");
        verifyMessage(msgs, idx++, 153, 15, "'assert' is not followed by whitespace.");
    }

}
