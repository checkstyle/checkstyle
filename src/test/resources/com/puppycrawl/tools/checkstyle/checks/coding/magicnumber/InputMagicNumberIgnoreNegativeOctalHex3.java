/*
MagicNumber
ignoreNumbers = -9223372036854775808, -2147483648, -1, 0, 1, 2, -2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

class InputMagicNumberIgnoreNegativeOctalHex3 {

    public int hashCode() {
        return 31; // violation
    }


    public int hashCode(int val) {
        return 42; // violation
    }


    public int hashcode() {
        return 13; // violation
    }

    static {
        int x=21; // violation
    }

    {
        int y=37; // violation
    }

    public InputMagicNumberIgnoreNegativeOctalHex3() {
        int z=101; // violation
    }

    @InputMagicNumberIntMethodAnnotation(42)
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value=43)
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44)
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value=-45)
    public void anotherNegative2() {
    }
}

class TestMethodCallIgnoreNegativeOctalHex3 {

        public TestMethodCallIgnoreNegativeOctalHex3(int x){

    }

        public void method2() {
        final TestMethodCallIgnoreNegativeOctalHex3 dummyObject =
                new TestMethodCallIgnoreNegativeOctalHex3(62);
        }
}

class BinaryIgnoreNegativeOctalHex3 {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValueIgnoreNegativeOctalHex3 {
    int value() default 101;
    int[] ar() default {102};
}
class AIgnoreNegativeOctalHex3 {
    {
        switch (Blah2IgnoreNegativeOctalHex1.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
