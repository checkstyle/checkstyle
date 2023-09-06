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
    record Mtr (String string, Record rec){ // violation ''(' is preceded with whitespace'
        private boolean inRecord (Object obj) { // violation ''(' is preceded with whitespace'
            int value = 0;
            if(obj instanceof Integer i) {
                value = i;
            }
            return value > 10;
        }

    }

    //in ctor
    record Mtr2 () { // violation ''(' is preceded with whitespace'
        Mtr2 (String s1, String s2, String s3){ // violation ''(' is preceded with whitespace'
            this ();
        }
    }

    record Mtr3 (Integer i, Node node) { // violation ''(' is preceded with whitespace'
        public static void main (String... args) { // violation ''(' is preceded with whitespace'
            System.out.println("works!");
        }
    }

    //in method def
    record Mtr4 () { // violation ''(' is preceded with whitespace'
        void foo (){} // violation ''(' is preceded with whitespace'
    }

    //in ctor invocation
    record Mtr5() {
        static Mtr mtr =
                new Mtr ("my string", new Mtr4()); // violation ''(' is preceded with whitespace'
    }

    //in ctor invocation
    class MyTestClass {
        private Mtr mtr =
                new Mtr ("my string", new Mtr4()); // violation ''(' is preceded with whitespace'
    }
}
