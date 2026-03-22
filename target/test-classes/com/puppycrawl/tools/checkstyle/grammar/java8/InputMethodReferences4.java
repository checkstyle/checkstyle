/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

//Issue #2729
package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.Arrays;


public class InputMethodReferences4 { // ok
    public void doSomething(final Object... arguments) {
        Arrays.stream(arguments)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }
}
