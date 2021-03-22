package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */
class InputJavadocParamOrderIncorrect { // ok

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param p2 Parameter 2 desc.  // violation
     * @param p1 Parameter 1 desc.  // ok
     * @param <T> Parameter T desc. // violation
     * @see Some javadoc.
     * @param p3 Parameter 3 desc.  // ok
     * @return Some number. // ok
     */
    static <T> int test(String p1, String p2, T p3) { // ok
        return 2021;
    }

    /**
     * Description
     *
     * @param <i Parameter i desc.     // ok, param does not exists
     * @param str Parameter str desc.  // ok
     */
    void test2(int i, String str) { // ok
    }

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <E> Parameter E desc.       // ok
     * @param <E> Parameter E desc.       // ok, duplicate param tag
     * @param CCC Parameter CCC desc.     // ok, param does not exists
     * @param array Parameter array desc. // ok
     * @see Some javadoc.
     * @param e Parameter e desc.         // violation
     * @param str Parameter str desc.     // violation
     * @return Some number.
     */
    <T, E, C> int sampleMethod(T[] array, String str, E e ) { // ok
        return 2021;
    }

    /**
     * Description.
     *
     * @param str Parameter str desc. // violation
     * @param i Parameter i desc.     // violation
     */
    public void add(int i, String str) { // ok

    }

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <T Parameter T desc          // ok, param does not exists
     * @param array Parameter array desc.  // ok
     * @see Some javadoc.
     * @return Some number.
     */
    public static <T> int sampleMethod(T[] array) { // ok
        return 2021;
    }

    /**
     * Description.
     *
     * @version Some javadoc.
     * @param <> Parameter T desc.        // ok, param does not exists
     * @param array Parameter array desc. // ok
     * @see Some javadoc.
     * @return Some number.
     */
    public static <T> int sampleMethod2(T[] array) { // ok
        return 2021;
    }

    /**
     * constructor
     *
     * @param <i Parameter i desc. // ok, param does not exists
     */
    public InputJavadocParamOrderIncorrect (int i) { // ok
    }

}
