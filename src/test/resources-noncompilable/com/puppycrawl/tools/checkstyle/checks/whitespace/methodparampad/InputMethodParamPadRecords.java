/*
MethodParamPad
allowLineBreaks = true
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

import org.w3c.dom.Node;

public class InputMethodParamPadRecords {

    // in record def
    record MyTestRecord (String string, Record rec){ // violation
        private boolean inRecord (Object obj) { // violation
            int value = 0;
            if(obj instanceof Integer i) {
                value = i;
            }
            return value > 10;
        }

    }

    //in ctor
    record MyTestRecord2 () { // violation
        MyTestRecord2 (String one, String two, String three){ // violation
            this ();// ok
        }
    }

    record MyTestRecord3 (Integer i, Node node) { // violation
        public static void main (String... args) { // violation
            System.out.println("works!");
        }
    }

    //in method def
    record MyTestRecord4 () { // violation
        void foo (){} // violation
    }

    //in ctor invocation
    record MyTestRecord5() {
        static MyTestRecord mtr =
                new MyTestRecord ("my string", new MyTestRecord4()); // violation
    }

    //in ctor invocation
    class MyTestClass {
        private MyTestRecord mtr =
                new MyTestRecord ("my string", new MyTestRecord4()); // violation
    }
}
