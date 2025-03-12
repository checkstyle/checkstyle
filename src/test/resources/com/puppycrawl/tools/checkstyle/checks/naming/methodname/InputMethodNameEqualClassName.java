/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

/**
 * Test input for MethodNameCheck specifically
 * whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethodNameEqualClassName {

        //illegal name
    public int InputMethodNameEqualClassName() { // 2 violations
        return 0;
    }

    //illegal name
    // violation 'PRIVATEInputMethodNameEqualClassName' must match the pattern
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
                //illegal name
        public int Inner() { // 2 violations
                        return 0;
                }

                //OK name - name of the outter class's ctor
        // violation 'InputMethodNameEqualClassName' must match the pattern
        public int InputMethodNameEqualClassName() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            public int InputMethodNameEqualClassName() { // 2 violations
                                return 1;
                        }
                };
        }
}

interface SweetInterface {

        //illegal name
    int SweetInterface(); // 2 violations
}

class Outer {

        //illegal name
    public void Outer() { // 2 violations

        }
}
