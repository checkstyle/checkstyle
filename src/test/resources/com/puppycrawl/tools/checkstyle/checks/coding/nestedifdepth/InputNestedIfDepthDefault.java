/*
NestedIfDepth
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

public class InputNestedIfDepthDefault
{
    void foo() {
        // nesting == 0
        if (true) {
        }

        // nesting == 1
        if (true) {
            if (true) {
            }
        }

        // nesting == 2
        if (true) {
            if (true) {
                if (true) { // violation
                }
            }
        }
    }

    void fooWithElse() {
        // nesting == 0
        if (true) {
        } else {
        }

        // nesting == 1
        if (true) {
            if (true) {
            } else {
            }
        } else {
            if (false) {
            } else {
            }
        }

        // nesting == 2
        if (true) {
            if (true) {
                if (true) { // violation
                } else {
                }
            } else {
                if (false) {
                } else {
                }
            }
        } else {
            if (true) {
                if (true) {
                } else {
                }
            } else {
                if (false) {
                } else {
                }
            }
        }
    }
}
