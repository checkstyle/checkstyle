////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks;

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
            int z = 1;
            int y = z;
        }

        if (x == 1)
        { // OK
            x = 2;
        }

        // case statements are a bit complicated,
        // they do not have its own variable scope by default.
        // Hence it may be OK in some development teams to allow
        // nested blocks if they are the complete case body.
        switch (x)
        {
            case 0:
                // OK
                x = 3;
                break;
            case 1:
                // Not OK, SLIST is not complete case body
                {
                    x = 1;
                }
                break;
            case 2:
                // OK if allowInSwitchCase is true, SLIST is complete case body
                {
                    x = 1;
                    break;
                }
            case 3: // test fallthrough
            default:
                // Not OK, SLIST is not complete case body
                System.identityHashCode("Hello");
                {
                    x = 2;
                }
        }
    }
}
