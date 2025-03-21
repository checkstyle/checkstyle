/*
 * com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck
 * id = id1
 *
 * com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
 * id = id2
 * format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
 *
 * com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck
 * id = id3
 *
 * com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck
 * id = id4
 * format = (default)^[a-z][a-zA-Z0-9]*$
 */

package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSorting {

    // ConstantNameCheck violation - should match all caps pattern
    private static final int someConstant = 42;

    // ParameterNameCheck violation - parameter should start with lowercase letter
    public void testCatch(int BADparam) {

        try {
            int x = 1 / 0;
        }

        // IllegalCatchCheck violation - catching generic Exception is disallowed
        // WhitespaceAfterCheck violation - 'catch' not followed by whitespace
        catch(Exception e) {
            System.out.println("Caught an exception");
        }
    }
}
