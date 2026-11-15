/*
NestedIfDepth
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

public class InputNestedIfDepthElseBlocks
{
    void elseChain() {
        // nesting == 2, ifs in else blocks count towards the depth
        if (true) {
        } else {
            if (true) {
            } else {
                if (false) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                } else {
                }
            }
        }
    }

    void elseIfChain() {
        // no nesting, a plain else-if chain is a single if-else statement
        if (true) {
        } else if (true) {
        } else if (false) {
        } else {
        }
    }

    void singleIfInElseBlock() {
        // nesting == 1
        if (true) {
        } else {
            if (true) {
            } else {
            }
        }
    }

    void ifInElseBlockWithSiblingStatement() {
        // nesting == 2
        if (true) {
        } else {
            int x = 1;
            if (true) {
                if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                }
            }
            System.out.println(x);
        }
    }

    void ifInsideChainLinkInElseBlock() {
        // nesting == 2, the if in the else block is a chain link and adds
        // no depth itself, but the if in its block counts the outer ifs
        if (true) {
            if (true) {
            } else {
                if (true) {
                    if (true) { // violation 'Nested if-else depth is 2 (max allowed is 1)'
                    }
                }
            }
        }
    }
}
