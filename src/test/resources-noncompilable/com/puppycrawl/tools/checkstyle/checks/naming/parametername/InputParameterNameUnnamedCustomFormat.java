/*
ParameterName
format = ^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

// non-compiled with javac: but was compiled on jdk before 9, so we need to continue to support
package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameUnnamedCustomFormat {
    void method(int _) { // violation 'Name '_' must match pattern'
    }
}
