/*
BooleanExpressionComplexity
max = (default)3
tokens = (default)LAND, BAND, LOR, BOR, BXOR


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityRecordsAndCompactCtors {

    record MyRecord1(boolean a, boolean b) {
        private boolean myBool() {
           return  (a & b) ^ (a || b) | a; // violation
        }
    }

    record MyRecord2(String myString, boolean a, boolean b) {

        // in compact ctor
        public MyRecord2{
            boolean d = (a & b) ^ (a || b) | a; // violation
        }
    }

    record MyRecord3(int x) {

        // in ctor
        MyRecord3(){
            this(3);
            boolean b = true;
            boolean a = true;
            boolean d = (a & b) ^ (a || b) | a; // violation
        }
    }

    record MyRecord4(int y) {
        private record MyRecord5(int z) {
            // in nested record in compact ctor
            public MyRecord5{
                boolean b = false;
                boolean a = true;
                boolean d = (a & b) ^ (a || b) | a; // violation
            }
        }
    }
}
