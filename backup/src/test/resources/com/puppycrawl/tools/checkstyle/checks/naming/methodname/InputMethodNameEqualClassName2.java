/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = true
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
public class InputMethodNameEqualClassName2 {

        //illegal name
    public int InputMethodNameEqualClassName2() { // violation
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethodNameEqualClassName() { // violation
        return 0;
    }

    class Inner {
                //illegal name
        public int Inner() { // violation
                        return 0;
                }

                //OK name - name of the outter class's ctor
        public int InputMethodNameEqualClassName2() { // violation
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            public int InputMethodNameEqualClassName2() { // violation
                                return 1;
                        }
                };
        }
}

interface SweetInterface2 {

        //illegal name
    int SweetInterface(); // violation
}

class Outer2 {

        //illegal name
    public void Outer() { // violation

        }
}
