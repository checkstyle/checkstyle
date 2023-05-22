/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = \tpublic


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameWhitespaceInAccessModifierProperty {

    public InputParameterNameWhitespaceInAccessModifierProperty(int parameter1) {} // violation

    public void v1(int h) { // ok
        new Object () {
            public void i(int parameter2) {} // violation
        };
    }

    void method2(int V3) {}
}
