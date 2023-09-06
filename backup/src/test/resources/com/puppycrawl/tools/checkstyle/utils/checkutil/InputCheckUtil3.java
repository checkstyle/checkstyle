/*
com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtil3 {

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
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
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
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
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
