////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Tests having inner types
 * @author Oliver Burn
 **/
class InputInner
{
    // Ignore - two errors
    class InnerInner2
    {
        // Ignore
        public int data;
    }

    // Ignore - 2 errors
    interface InnerInterface2
    {
        // Ignore - should be all upper case
        String data = "zxzc";

        // Ignore
        class InnerInterfaceInnerClass
        {
            // Ignore - need Javadoc and made private
            int data;
        }
    }
}
