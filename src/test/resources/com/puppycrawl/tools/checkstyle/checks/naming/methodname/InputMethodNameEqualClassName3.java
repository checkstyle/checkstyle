/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = true
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

/**
 * Test input for MethodNameCheck specifically
 * whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethodNameEqualClassName3 {

        //illegal name
    // violation 'InputMethodNameEqualClassName3' must match the pattern
    public int InputMethodNameEqualClassName3() {
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
                //illegal name
        // violation 'Inner' must match the pattern
        public int Inner() {
                        return 0;
                }

                //OK name - name of the outter class's ctor
        // violation 'InputMethodNameEqualClassName3' must match the pattern
        public int InputMethodNameEqualClassName3() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            // violation 'InputMethodNameEqualClassName3' must match the pattern
            public int InputMethodNameEqualClassName3() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface3 {

        //illegal name
    // violation 'SweetInterface' must match the pattern
    int SweetInterface();
}

class Outer3 {

        //illegal name
    // violation 'Outer' must match the pattern
    public void Outer() {

        }
}
