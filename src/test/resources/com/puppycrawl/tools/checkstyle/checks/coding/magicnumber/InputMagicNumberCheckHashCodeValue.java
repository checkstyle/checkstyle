/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

public class InputMagicNumberCheckHashCodeValue {

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

    public InputMagicNumberCheckHashCodeValue() {
        int z=101; // violation
    }

    @InputMagicNumberIntMethodAnnotation(42) // violation
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value=43) // violation
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44) // violation
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value=-45) // violation
    public void anotherNegative2() {
    }
}

class TestMethodCallHashCodeValue {

        public TestMethodCallHashCodeValue(int x){

    }

        public void method2() {
        final TestMethodCallHashCodeValue dummyObject = new TestMethodCallHashCodeValue(62);
        }
}

class BinaryCheckHashCodeValue {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValueCheckHashCodeValue {
    int value() default 101;
    int[] ar() default {102};
}
class ADefault3 {
    {
        switch (Blah2CheckDefault.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
