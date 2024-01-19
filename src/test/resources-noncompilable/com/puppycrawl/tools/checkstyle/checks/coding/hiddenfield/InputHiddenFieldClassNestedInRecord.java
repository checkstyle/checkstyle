/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = true
setterCanReturnItsClass = true
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldClassNestedInRecord {

    record foo(int i) {

        class foo2 {
            int a, b;

            foo setA(int a) { // violation
                this.a = a;
                return foo.this;
            }

            foo2 setB(int b) {
                this.b = b;
                return this;
            }
        }

    }
}
