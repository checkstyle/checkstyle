/*
ParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support
package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameUnnamed {
    void method(int _) {
    }

    InputParameterNameUnnamed(int _) {
    }

    void badName(int BadName) { // violation 'Name 'BadName' must match pattern'
    }

    void doubleUnderscore(int __) { // violation 'Name '__' must match pattern'
    }

    void goodName(int goodName) {
    }
}

interface UnnamedParameterInterface {
    void method(int _);
}
