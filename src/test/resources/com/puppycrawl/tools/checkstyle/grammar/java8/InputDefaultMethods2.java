/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

public class InputDefaultMethods2 { // ok

    public void doSomething(){
        int a = 5;
        switch (a)
        {
        case 0:
            break;

        default:
            break;

        }

    }

}
