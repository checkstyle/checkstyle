////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for finding nested blocks.
 * @author lkuehne
 **/
class InputNestedBlocks
{
    static
    { // OK
    }

    public void method()
    {
        int x = 0;

        // if (condition that is not important anymore)
        { // nested block, should be marked
            int x = 1;
            int y = x;
        }

        if (x == 1)
        { // OK
            x = 2;
        }

        switch (x)
        {
            case 0:
                x = 3; // OK
                break;
            default:
                {   // should be marked, even if a switch
                    // case does not have its own scope
                    x = 2;
                }
        }
    }
}
