package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since 8.36 // ok
 */
class InputFile2 {

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param p2
     *            Parameter 2 desc. // violation
     * @param p1
     *            Parameter 1 desc.
     * @param <T>
     *            Parameter T desc. // violation
     * @see Some javadoc. // OK
     * @param p3
     *            Parameter 3 desc.
     * @return Some number. // OK
     */
    public <T> int test(String p1, String p2, T p3) {
        return 2021;
    }

    /**
     * Description
     *
     * @param str
     *            Parameter str desc. // violation
     */
    public void test2(int i, String str) {
    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <E>
     *            Parameter E desc. // violation
     * @param <T>
     *            Parameter T desc. // violation
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @param ele
     *            Parameter ele desc. // violation
     * @param str
     *            Parameter str desc. // violation
     * @return Some number. // OK
     */
    public static <T, E> int sampleMethod(T[] array, String str, E ele ) {
        return 2021;
    }

    /**
     * Description.
     *
     * @param s
     *            Parameter s desc. // violation
     * @param t
     *            Parameter t desc. // violation
     */
    public void add(int t, String s) {

    }

}
