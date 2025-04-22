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
    // 2 violations 3 lines below:
    //        'Method Name 'InputMethodNameEqualClassName' must not equal the enclosing class name.'
    //        'Name 'InputMethodNameEqualClassName' must match pattern.'
    public int InputMethodNameEqualClassName() {
        return 0;
    }

    //illegal name
    // violation below 'Name 'PRIVATEInputMethodNameEqualClassName' must match pattern.'
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
        //illegal name
        // 2 violations 3 lines below:
        //        'Method Name 'Inner' must not equal the enclosing class name.'
        //        'Name 'Inner' must match pattern.'
        public int Inner() {
                        return 0;
        }

        //OK name - name of the outter class's ctor
        // violation below 'Name 'InputMethodNameEqualClassName' must match pattern.'
        public int InputMethodNameEqualClassName() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

        //illegal name
        // 2 violations 3 lines below:
        //  'Method Name 'InputMethodNameEqualClassName' must not equal the enclosing class name.'
        //  'Name 'InputMethodNameEqualClassName' must match pattern.'
            public int InputMethodNameEqualClassName() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface {

    //illegal name
    // 2 violations 3 lines below:
    //       'Method Name 'SweetInterface' must not equal the enclosing class name.'
    //       'Name 'SweetInterface' must match pattern.'
    int SweetInterface();
}

class Outer {

    //illegal name
    // 2 violations 3 lines below:
    //       'Method Name 'Outer' must not equal the enclosing class name.'
    //       'Name 'Outer' must match pattern.'
    public void Outer() {

    }
}
