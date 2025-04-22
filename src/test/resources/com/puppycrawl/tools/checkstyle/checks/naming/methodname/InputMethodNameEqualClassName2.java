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
    // violation below 'Name 'InputMethodNameEqualClassName2' must match pattern.'
    public int InputMethodNameEqualClassName2() {
        return 0;
    }

    //illegal name
    // violation below 'Name 'PRIVATEInputMethodNameEqualClassName' must match pattern.'
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
        //illegal name
        // violation below 'Name 'Inner' must match pattern.'
        public int Inner() {
                        return 0;
                }

        //OK name - name of the outter class's ctor
        // violation below 'Name 'InputMethodNameEqualClassName2' must match pattern.'
        public int InputMethodNameEqualClassName2() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

            //illegal name
            // violation below 'Name 'InputMethodNameEqualClassName2' must match pattern.'
            public int InputMethodNameEqualClassName2() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface2 {

        //illegal name
    int SweetInterface(); // violation 'Name 'SweetInterface' must match pattern'
}

class Outer2 {

        //illegal name
    public void Outer() { // violation 'Name 'Outer' must match pattern'

        }
}
