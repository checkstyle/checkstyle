/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;


public final class InputMethodReferences6 { // ok

    public void testMethod() {

        @SuppressWarnings("unused")
        class MyClass {
            public int doSomething() {
                return 0;
            }
        }

    }

}
