package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

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
    public <T> int test(String p1, String p2, T p3) { // violation
        return 2021;
    }

    /**
     * Description
     *
     * @param <i>
     *            Parameter i desc. // violation
     * @param str
     *            Parameter str desc.
     */
    public void test2(int i, String str) { // violation
    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <E>
     *            Parameter E desc. // violation
     * @param <E>
     *            Parameter E desc.
     * @param CCC
     *            Parameter CCC desc. // violation
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @param e
     *            Parameter e desc. // violation
     * @param str
     *            Parameter str desc. // violation
     * @return Some number. // OK
     */
    public static <T, E, C> int sampleMethod(T[] array, String str, E e ) { // violation
        return 2021;
    }

    /**
     * Description.
     *
     * @param str
     *            Parameter str desc. // violation
     * @param i
     *            Parameter i desc. // violation
     */
    public void add(int i, String str) { // violation

    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <T
     *            Parameter T desc. // violation
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @return Some number. // OK
     */
    public static <T> int sampleMethod(T[] array) { // violation
        return 2021;
    }

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param <>
     *            Parameter T desc. // violation
     * @param array
     *            Parameter array desc.
     * @see Some javadoc. // OK
     * @return Some number. // OK
     */
    public static <T> int sampleMethod2(T[] array) { // violation
        return 2021;
    }

}
