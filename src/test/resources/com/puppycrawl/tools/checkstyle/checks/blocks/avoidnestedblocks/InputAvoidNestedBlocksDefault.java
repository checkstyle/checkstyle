/*
AvoidNestedBlocks
allowInSwitchCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.avoidnestedblocks;

/**
 * Test case for finding nested blocks.
 * @author lkuehne
 **/
class InputAvoidNestedBlocksDefault
{
    static
    {
    }

    public void method()
    {
        int x = 0;

        // if (condition that is not important anymore)
        { // violation, 'avoid nested blocks'
            int z = 1;
            int y = z;
        }

        if (x == 1)
        {
            x = 2;
        }

        // case statements are a bit complicated,
        // they do not have its own variable scope by default.
        // Hence, it may be ok in some development teams to allow
        // nested blocks if they are the complete case body.
        switch (x)
        {
            case 0:

                x = 3;
                break;
            case 1:
                // Not ok, SLIST is not complete case body
                { // violation, 'avoid nested blocks'
                    x = 1;
                }
                break;
            case 2:
                // ok if allowInSwitchCase is true, SLIST is complete case body
                { // violation, 'avoid nested blocks'
                    x = 1;
                    break;
                }
            case 3: // test fallthrough
            default:
                // Not ok, SLIST is not complete case body
                System.identityHashCode("Hello");
                { // violation, 'avoid nested blocks'
                    x = 2;
                }
        }
    }
}
