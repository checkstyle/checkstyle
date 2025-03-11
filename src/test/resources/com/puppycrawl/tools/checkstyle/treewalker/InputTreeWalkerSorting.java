/*
com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck,
com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck,
com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck,
com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck

*/
package com.puppycrawl.tools.checkstyle.treewalker;

class InputTreeWalkerSorting {

    private static final int someConstant = 42;
    // violation above, 'Name 'someConstant' must match pattern.'

    public void testCatch(int BADparam) { // violation, 'Name 'BADparam' must match pattern.'
        try {
            int x = 1 / 0;
        } catch(Exception e) { // violation, 'Catching 'Exception' is not allowed.',
            // violation above, ''catch' is not followed by whitespace.'
            System.out.println("Caught an exception");
        }
    }
}
