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
    // violation below 'Method name 'InputMethodNameEqualClassName2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    public int InputMethodNameEqualClassName2() {
        return 0;
    }

    //illegal name
    // violation below 'Method name 'PRIVATEInputMethodNameEqualClassName' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
                //illegal name
        // violation below 'Method name 'Inner' must match the pattern '^[a-z][a-zA-Z0-9]*$''
        public int Inner() {
                        return 0;
                }

                //OK name - name of the outter class's ctor
        // violation below 'Method name 'InputMethodNameEqualClassName2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
        public int InputMethodNameEqualClassName2() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            // violation below 'Method name 'InputMethodNameEqualClassName2' must match the pattern '^[a-z][a-zA-Z0-9]*$''
            public int InputMethodNameEqualClassName2() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface2 {

        //illegal name
    // violation below 'Method name 'SweetInterface' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    int SweetInterface();
}

class Outer2 {

        //illegal name
    // violation below 'Method name 'Outer' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    public void Outer() {

        }
}
