package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

/**
 * Test input for MethodNameCheck specifically
 * whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethodNameEqualClassName {

        //illegal name
    public int InputMethodNameEqualClassName() {
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethodNameEqualClassName() {
        return 0;
    }

    class Inner {
                //illegal name
        public int Inner() {
                        return 0;
                }

                //OK name - name of the outter class's ctor
        public int InputMethodNameEqualClassName() {
                        return 0;
                }
        }

        public void anotherMethod() {
                new InputMethodNameEqualClassName() {

                        //illegal name
            public int InputMethodNameEqualClassName() {
                                return 1;
                        }
                };
        }
}

interface SweetInterface {

        //illegal name
    int SweetInterface();
}

class Outer {

        //illegal name
    public void Outer() {

        }
}
