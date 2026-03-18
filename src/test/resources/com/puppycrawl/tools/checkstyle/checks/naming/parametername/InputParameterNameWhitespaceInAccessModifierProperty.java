/*
ParameterName
format = ^h$
ignoreOverridden = (default)false
accessModifiers = \tpublic


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameWhitespaceInAccessModifierProperty {

    public InputParameterNameWhitespaceInAccessModifierProperty(int parameter1) {}
    // violation above, ''parameter1'' must match pattern ''^h$''

    public void v1(int h) {
        new Object () {
            public void i(int parameter2) {}
            // violation above, ''parameter2'' must match pattern ''^h$''
        };
    }

    void method2(int V3) {}
}
