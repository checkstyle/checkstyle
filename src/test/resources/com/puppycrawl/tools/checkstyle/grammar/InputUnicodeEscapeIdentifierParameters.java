/*
com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.grammar;

public class InputUnicodeEscapeIdentifierParameters {

    void valid(int \u0061bc) { }

    void start(int \u0041bc) { } // violation "Name 'Abc' must match"
    void middle(int a\u00e9b) { } // violation "Name 'aéb' must match"
    void after(int \u0061bc, int \u0042de) { } // violation "Name 'Bde' must match"
}
