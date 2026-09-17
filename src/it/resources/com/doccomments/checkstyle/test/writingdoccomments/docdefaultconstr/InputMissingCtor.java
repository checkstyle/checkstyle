package com.doccomments.checkstyle.test.writingdoccomments.docdefaultconstr;

/**
 * Input with a non-public explicit constructor.
 */
public class InputMissingCtor {

    /** Stored value. */
    private int a;

    /**
     * Creates a sample instance.
     *
     * @param a value to store
     */
    InputMissingCtor(int a) {
        this.a = a;
    }

}

/**
 * Input with an explicit default constructor.
 */
class ExampleDefaultCtor {

    /** Stored text. */
    private String s;

    /**
     * Creates a sample instance.
     */
    ExampleDefaultCtor() {
        s = "string";
    }

}
/**
 * Input without an explicit constructor.
 */
// violation 3 lines below 'Class should define an explicit constructor.
//      If this class was already released with an implicit constructor,
//      preserve its generated access modifier for compatibility.'
class InvalidExample {

    /**
     * Runs the sample.
     */
    public void test() {}

}

/**
 * Abstract sample input.
 */
abstract class AbstractExample {

    /**
     * Runs the sample.
     */
    public abstract void test();

}
