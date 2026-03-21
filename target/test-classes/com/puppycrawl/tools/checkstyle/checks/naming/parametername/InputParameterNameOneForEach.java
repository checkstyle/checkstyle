/*
ParameterName
format = ^a[A-Z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private



*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/** Test class for variable naming in for each clause. */
public class InputParameterNameOneForEach {

    /** Some more Javadoc. */
    public void doSomething() {
        // "O" should violate the pattern
        for (Object O : new java.util.ArrayList()) {
        }
    }
}
