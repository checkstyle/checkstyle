/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberDefault3 {

    public int hashCode() {
        return 31; // violation ''31' is a magic number'
    }


    public int hashCode(int val) {
        return 42; // violation ''42' is a magic number'
    }


    public int hashcode() {
        return 13; // violation ''13' is a magic number'
    }

    static {
        int x=21; // violation ''21' is a magic number'
    }

    {
        int y=37; // violation ''37' is a magic number'
    }

    public InputMagicNumberDefault3() {
        int z=101; // violation ''101' is a magic number'
    }

    @InputMagicNumberIntMethodAnnotation(42) // violation ''42' is a magic number'
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value=43) // violation ''43' is a magic number'
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44) // violation ''-44' is a magic number'
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value=-45) // violation ''-45' is a magic number'
    public void anotherNegative2() {
    }
}

class TestMethodCallDefault3 {

        public TestMethodCallDefault3(int x){

    }

        public void method2() {
        final TestMethodCallDefault3 dummyObject = new TestMethodCallDefault3(62);
        }
}

class BinaryDefault3 {
    int intValue = 0b101; // violation ''0b101' is a magic number'
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation ''0b1010000101000101101000010100010110100001010001011010000101000101L' is a magic number'
}
@interface AnnotationWithDefaultValueDefault3 {
    int value() default 101;
    int[] ar() default {102};
}
class ADefault3 {
    {
        switch (Blah2Default1.LOW) {
        default:
            int b = 122; // violation ''122' is a magic number'
        }
    }
}
