/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = true
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG


*/

package com.puppycrawl.tools.checkstyle.checks.coding.magicnumber;

class InputMagicNumberIgnoreHashCodeMethod3 {

    public int hashCode() {
        return 31;
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

    public InputMagicNumberIgnoreHashCodeMethod3() {
        int z = 101; // violation '101' is a magic number
    }

    @InputMagicNumberIntMethodAnnotation(42)
    public void another() {
    }

    @InputMagicNumberIntMethodAnnotation(value = 43)
    public void another2() {
    }

    @InputMagicNumberIntMethodAnnotation(-44)
    public void anotherNegative() {
    }

    @InputMagicNumberIntMethodAnnotation(value = -45)
    public void anotherNegative2() {
    }
}

class TestMethodCallIgnoreHashCodeMethod3 {

    public TestMethodCallIgnoreHashCodeMethod3(int x) {

    }

    public void method2() {
        final TestMethodCallIgnoreHashCodeMethod3 dummyObject = new TestMethodCallIgnoreHashCodeMethod3(62);
    }
}

class BinaryIgnoreHashCodeMethod3 {
    int intValue = 0b101; // violation '0b101' is a magic number
    long l = 0b1010000101000101101000010100010110100001010001011010000101000101L; // violation
                                                                                  // '0b1010000101000101101000010100010110100001010001011010000101000101L'
                                                                                  // is a magic number
}

@interface AnnotationWithDefaultValueIgnoreHashCodeMethod3 {
    int value() default 101;

    int[] ar() default { 102 };
}

class AIgnoreHashCodeMethod3 {
    {
        switch (Blah2IgnoreHashCodeMethod1.LOW) {
            default:
                int b = 122; // violation '122' is a magic number
        }
    }
}
