/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 19/09/2002
 * Time: 18:35:19
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;

public class TreeWalkerTest
    extends TestCase
{
    public TreeWalkerTest(String s)
    {
        super(s);
    }

    public void testCreate()
    {
        new TreeWalker(new LocalizedMessages(0), 8);
        assertTrue(true);
    }
}
