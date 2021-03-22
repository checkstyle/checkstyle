package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc
 */
public class InputFile3 {

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @return int
     */
    public <T> int test(String p1, String p2, T p3) {
        return 2021;
    }

    public void test2(int i, String str) {
    }

    public static <T, E> int sampleMethod(T[] array, String str, E ele ) {
        return 2021;
    }

    /**
     * Description.
     *
     * @see Some javadoc. // OK
     */
    public void add(int t, String s) {

    }
}
