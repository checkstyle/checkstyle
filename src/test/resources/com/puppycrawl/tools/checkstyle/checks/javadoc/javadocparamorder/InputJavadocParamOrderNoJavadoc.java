package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc
 */
public class InputJavadocParamOrderNoJavadoc { // ok

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @return int
     */
    public <T> int test(String p1, String p2, T p3) { // ok
        return 2021;
    }

    public void test2(int i, String str) {
    }

    public static <T, E> int sampleMethod(T[] array, String str, E e ) { // ok
        return 2021;
    }

    /**
     * Description.
     *
     * @see Some javadoc. // OK
     */
    public void add(int t, String s) { // ok

    }
}
