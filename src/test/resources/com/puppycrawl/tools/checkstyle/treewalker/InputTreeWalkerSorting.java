/*
com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck

*/
package com.puppycrawl.tools.checkstyle.treewalker;
class InputTreeWalkerSorting {

    private final int foo = 0;

    public void testCatch() {
        try {
            int x = 1 / 0;
        } catch (Exception e) { // // violation , 'Catching 'Exception' is not allowed.'
            System.out.println("Caught an exception");
        }
    }
}
