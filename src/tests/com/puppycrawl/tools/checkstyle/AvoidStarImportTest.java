package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;

public class AvoidStarImportTest
extends AbstractCheckTestCase
{
    public AvoidStarImportTest(String aName)
    {
        super(aName);
    }

    public void testIt()
        throws Exception
    {
        final LocalizedMessages msgs = new LocalizedMessages(8);
        final TreeWalker walker = new TreeWalker(msgs);
        final CheckConfiguration config = new CheckConfiguration();
        config.setClassname(AvoidStarImport.class.getName());
        final Check c = config.createInstance(
            Thread.currentThread().getContextClassLoader());
        walker.registerCheck(c, config);
        final String fname = CheckerTest.getPath("InputImport.java");
        final String[] lines = getLines(fname);
        walker.walk(getAST(fname, lines), lines, fname);
        assertEquals(msgs.getMessages().length, 3);
        verifyMessage(msgs, 0, 7, 1, "Avoid using the '.*' form of import.");
        verifyMessage(msgs, 1, 9, 1, "Avoid using the '.*' form of import.");
        verifyMessage(msgs, 2, 10, 1, "Avoid using the '.*' form of import.");
    }

}
