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
    // violation below 'Name 'InputMethodNameEqualClassName3' must match pattern'
    public int InputMethodNameEqualClassName3() {
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
                //illegal name
        public int Inner() { // violation 'Name 'Inner' must match pattern'
                        return 0;
                }

                //OK name - name of the outter class's ctor
        // violation below  'Name 'InputMethodNameEqualClassName3' must match pattern'
        public int InputMethodNameEqualClassName3() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            // violation below  'Name 'InputMethodNameEqualClassName3' must match pattern'
            public int InputMethodNameEqualClassName3() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface3 {

        //illegal name
    int SweetInterface(); // violation 'Name 'SweetInterface' must match pattern'
}

class Outer3 {

        //illegal name
    public void Outer() { // violation 'Name 'Outer' must match pattern'

        }
}
