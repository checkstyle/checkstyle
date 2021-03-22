package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamordercheck;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */
class InputJavadocParamOrderIncorrect {

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
     * @param <i>
     *            Parameter str desc. // violation
     * @param str
     *            Parameter str desc.
     */
    public void test2(int i, String str) {
    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <E>
     *            Parameter E desc. // violation
     * @param <E>
     *            Parameter T desc.
     * @param CCC
     *            Parameter T desc. // violation
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @param e
     *            Parameter e desc. // violation
     * @param str
     *            Parameter str desc. // violation
     * @return Some number. // OK
     */
    public static <T, E, C> int sampleMethod(T[] array, String str, E e ) {
        return 2021;
    }

    /**
     * Description.
     *
     * @param str
     *            Parameter s desc. // violation
     * @param i
     *            Parameter t desc. // violation
     */
    public void add(int i, String str) {

    }

}
