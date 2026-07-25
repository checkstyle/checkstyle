package com.doccomments.checkstyle.test.writingdoccomments.docdefaultconstr;

/**
 * Input with a private explicit constructor.
 */
public class InputPrivateMissingCtor {

    /** Stored value. */
    private int a;

    /**
     * Creates a sample instance.
     *
     * @param a value to store
     */
    private InputPrivateMissingCtor(int a) {
        this.a = a;
    }

}

/**
 * Input with a private explicit constructor.
 */
class ExampleDefaultCtor1 {

    /** Stored text. */
    private String s;

    /**
     * Creates a sample instance.
     */
    private ExampleDefaultCtor1() {
        s = "string";
    }

}

/**
 * Input without an explicit constructor.
 */
// violation 3 lines below 'Class should define an explicit constructor.
//      If this class was already released with an implicit constructor,
//      preserve its generated access modifier for compatibility.'
class InvalidExample1 {

    /**
     * Runs the sample.
     */
    private void test() {}

}
