/*
UncommentedMain
excludedClasses = (default)^$


*/

package com.puppycrawl.tools.checkstyle.checks.uncommentedmain;

class InputUncommentedMain3 {

    public static void anyWrongMethodName(String[] args) // ok
    {
        System.identityHashCode("InputUncommentedMain.main()");
    }
}
