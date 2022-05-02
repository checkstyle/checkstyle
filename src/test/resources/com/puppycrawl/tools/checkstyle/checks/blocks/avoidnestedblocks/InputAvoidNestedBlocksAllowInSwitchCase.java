/*
AvoidNestedBlocks
allowInSwitchCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.avoidnestedblocks;

class InputAvoidNestedBlocksAllowInSwitchCase
{
    static
    { // ok
    }

    public void method()
    {
        int x = 0;

        // if (condition that is not important anymore)
        { // violation
            int z = 1;
            int y = z;
        }

        if (x == 1)
        { // ok
            x = 2;
        }

        // case statements are a bit complicated,
        // they do not have its own variable scope by default.
        // Hence, it may be ok in some development teams to allow
        // nested blocks if they are the complete case body.
        switch (x)
        {
            case 0:
                // ok
                x = 3;
                break;
            case 1:
                // Not ok, SLIST is not complete case body
                { // violation
                    x = 1;
                }
                break;
            case 2:
                // ok if allowInSwitchCase is true, SLIST is complete case body
                {
                    x = 1;
                    break;
                }
            case 3: // test fallthrough
            default:
                // Not ok, SLIST is not complete case body
                System.identityHashCode("Hello");
                { // violation
                    x = 2;
                }
        }
    }
}
