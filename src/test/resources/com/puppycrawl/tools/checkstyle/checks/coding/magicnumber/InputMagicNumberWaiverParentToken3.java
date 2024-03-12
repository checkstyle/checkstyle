/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = ASSIGN, ARRAY_INIT, EXPR, UNARY_PLUS, UNARY_MINUS, TYPECAST, \
                            ELIST, STAR, DIV, PLUS, MINUS
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

class InputMagicNumberWaiverParentToken3 {

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

    public InputMagicNumberWaiverParentToken3() {
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

    @InputMagicNumberIntMethodAnnotation(value=-45)  // violation
    public void anotherNegative2() {
    }
}

class TestMethodCallWaiverParentToken3 {

        public TestMethodCallWaiverParentToken3(int x){

    }

        public void method2() {
        final TestMethodCallWaiverParentToken3 dummyObject =
                new TestMethodCallWaiverParentToken3(62); // violation
        }
}

class BinaryWaiverParentToken3 {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValueWaiverParentToken3 {
    int value() default 101;
    int[] ar() default {102};
}
class A {
    {
        switch (Blah2WaiverParentToken1.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
@interface InputMagicNumberIntMethodAnnotation {
        int value();
}
