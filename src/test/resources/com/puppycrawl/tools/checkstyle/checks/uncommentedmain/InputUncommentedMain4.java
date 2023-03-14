/*
UncommentedMain
excludedClasses = (default)^$


*/

package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

class InputUncommentedMain4
{
    // one more uncommented main
    public static void main(int[] args) // ok
    {
        System.identityHashCode("test1.main()");
    }
}
