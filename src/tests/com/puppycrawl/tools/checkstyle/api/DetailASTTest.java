package com.puppycrawl.tools.checkstyle.api;

import junit.framework.TestCase;

/**
 * TestCase to check DetailAST.
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 */
public class DetailASTTest extends TestCase {

    public void testGetChildCount() {
        final DetailAST root = new DetailAST();
        final DetailAST firstLevelA = new DetailAST();
        final DetailAST firstLevelB = new DetailAST();
        final DetailAST secondLevelA = new DetailAST();
        
        root.setFirstChild(firstLevelA);
        
        firstLevelA.setParent(root);
        firstLevelA.setFirstChild(secondLevelA);
        firstLevelA.setNextSibling(firstLevelB);
        
        firstLevelB.setParent(root);
        
        secondLevelA.setParent(firstLevelA);
        
        assertEquals(0, secondLevelA.getChildCount());
        assertEquals(0, firstLevelB.getChildCount());
        assertEquals(1, firstLevelA.getChildCount());
        assertEquals(2, root.getChildCount());
        assertEquals(2, root.getChildCount());
        
        assertNull(root.getPreviousSibling());
        assertNull(firstLevelA.getPreviousSibling());
        assertNull(secondLevelA.getPreviousSibling());
        assertEquals(firstLevelA, firstLevelB.getPreviousSibling());
    }
}
