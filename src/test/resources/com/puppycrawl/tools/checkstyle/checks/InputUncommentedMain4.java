////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks;

class InputUncommentedMainTest4
{
    // one more uncommented main
    public static void main(int[] args)
    {
        System.identityHashCode("test1.main()");
    }
}
