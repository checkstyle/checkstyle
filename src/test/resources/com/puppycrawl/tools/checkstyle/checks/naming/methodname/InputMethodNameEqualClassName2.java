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
    public int InputMethodNameEqualClassName2() {  // violation ''InputMethodNameEqualClassName2' must match the pattern'
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethodNameEqualClassName() { // violation 'PRIVATEInputMethodNameEqualClassName' must match the pattern
        return 0;
    }

    class Inner {
                //illegal name
        public int Inner() { // violation 'Inner' must match the pattern
                        return 0;
                }

                //OK name - name of the outter class's ctor
        public int InputMethodNameEqualClassName2() { // violation 'InputMethodNameEqualClassName2' must match the pattern
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            public int InputMethodNameEqualClassName2() { // violation ''InputMethodNameEqualClassName2' must match the pattern'
                                return 1;
                        }
                };
        }
}

interface SweetInterface2 {

        //illegal name
    int SweetInterface(); // violation ''SweetInterface' must match the pattern'
}

class Outer2 {

        //illegal name
    public void Outer() { // violation ''Outer' must match the pattern'

        }
}
