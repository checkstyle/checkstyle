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

public class TreeWalkerTest
    extends TestCase
{
    public void testCreate()
    {
        // TODO: This test does not make sense, what does it actually test?
        GlobalProperties props = new GlobalProperties();
        new TreeWalker(props);
        assertTrue(true);
    }
}
