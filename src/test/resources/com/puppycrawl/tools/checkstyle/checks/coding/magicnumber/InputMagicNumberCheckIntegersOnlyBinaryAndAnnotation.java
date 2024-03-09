/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW
tokens = NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

class InputMagicNumberCheckIntegersOnlyBinaryAndAnnotation {

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

    public InputMagicNumberCheckIntegersOnlyBinaryAndAnnotation() {
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

class TestMethodBinaryAndAnnotation {

        public TestMethodBinaryAndAnnotation(int x){

    }

        public void method2() {
        final TestMethodBinaryAndAnnotation dummyObject = new TestMethodBinaryAndAnnotation(62);
        }
}

class BinaryIntegersOnlyBinaryAndAnnotation {
    int intValue = 0b101; // violation
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
}
@interface AnnotationWithDefaultValueIntegersOnlyBinaryAndAnnotation {
    int value() default 101;
    int[] ar() default {102};
}
class AIntegersOnlyBinaryAndAnnotation {
    {
        switch (Blah2IntegersOnlyViolationHandler.LOW) {
        default:
            int b = 122; // violation
        }
    }
}
