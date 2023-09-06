/*
NestedIfDepth
max = 2


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

public class InputNestedIfDepthMax
{
    void foo() {
        // nesting == 0
        if (true) { // ok
        }

        // nesting == 1
        if (true) { // ok
            if (true) { // ok
            }
        }

        // nesting == 2
        if (true) { // ok
            if (true) { // ok
                if (true) { // ok
                }
            }
        }
    }

    void fooWithElse() {
        // nesting == 0
        if (true) { // ok
        } else {
        }

        // nesting == 1
        if (true) { // ok
            if (true) { // ok
            } else {
            }
        } else {
            if (false) { // ok
            } else {
            }
        }

        // nesting == 2
        if (true) { // ok
            if (true) { // ok
                if (true) { // ok
                } else {
                }
            } else {
                if (false) { // ok
                } else {
                }
            }
        } else {
            if (true) { // ok
                if (true) { // ok
                } else {
                }
            } else {
                if (false) { // ok
                } else {
                }
            }
        }
    }
}
