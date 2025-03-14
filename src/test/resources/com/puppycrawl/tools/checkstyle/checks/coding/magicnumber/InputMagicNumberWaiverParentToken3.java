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
        return 31; // violation '31' is a magic number
    }

    public int hashCode(int val) {
        return 42; // violation '42' is a magic number
    }

    public int hashcode() {
        return 13; // violation '13' is a magic number
    }

    static {
        int x = 21; // violation '21' is a magic number
    }

    {
        int y = 37; // violation '37' is a magic number
    }

    public InputMagicNumberWaiverParentToken3() {
        int z = 101; // violation '101' is a magic number
    }

    @InputMagicNumberIntMethodAnnotation(42) // violation '42' is a magic number
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value = 43) // violation '43' is a magic number
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44) // violation '-44' is a magic number
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value = -45) // violation '-45' is a magic number
    public void anotherNegative2() {
    }
}

class TestMethodCallWaiverParentToken3 {

    public TestMethodCallWaiverParentToken3(int x) {

    }

    public void method2() {
        final TestMethodCallWaiverParentToken3 dummyObject = new TestMethodCallWaiverParentToken3(62); // violation '62'
                                                                                                       // is a magic
                                                                                                       // number
    }
}

class BinaryWaiverParentToken3 {
    int intValue = 0b101; // violation '0b101' is a magic number
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
                                                                                  // '0b1010000101000101101000010100010110100001010001011010000101000101L'
                                                                                  // is a magic number
}

@interface AnnotationWithDefaultValueWaiverParentToken3 {
    int value() default 101;

    int[] ar() default { 102 };
}

class A {
    {
        switch (Blah2WaiverParentToken1.LOW) {
            default:
                int b = 122; // violation '122' is a magic number
        }
    }
}

@interface InputMagicNumberIntMethodAnnotation {
    int value();
}
