package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.checks.RedundantModifierCheck;

public class RedundantModifierTest extends AbstractCheckTestCase
{
    public RedundantModifierTest(String aName)
    {
        super(aName);
    }

    public void testIt()
            throws Exception
    {
        final LocalizedMessages msgs = new LocalizedMessages(8);
        final TreeWalker walker = new TreeWalker(msgs);
        final CheckConfiguration config = new CheckConfiguration();
        config.setClassname(RedundantModifierCheck.class.getName());
        final Check c = config.createInstance(
            Thread.currentThread().getContextClassLoader());
        walker.registerCheck(c, config);
        final String fname = CheckerTest.getPath("InputModifier.java");
        final String[] lines = getLines(fname);
        walker.walk(getAST(fname, lines), lines, fname);
        assertEquals(2, msgs.getMessages().length);
        int idx = 0;
        verifyMessage(msgs, idx++, 32, 9, "Redundant 'public' modifier.");
        verifyMessage(msgs, idx++, 38, 9, "Redundant 'abstract' modifier.");
    }
}
