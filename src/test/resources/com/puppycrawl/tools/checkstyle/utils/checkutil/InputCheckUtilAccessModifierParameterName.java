/*
com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck
format = ^a[A-Z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = private


*/

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtilAccessModifierParameterName {

    private enum FOO {

        SOME_CONSTANT(1) {
        };

        FOO(int badFormat) { // violation
        }
    }
}
