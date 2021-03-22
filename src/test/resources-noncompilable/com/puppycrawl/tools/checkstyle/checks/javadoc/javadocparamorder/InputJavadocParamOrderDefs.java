//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

import java.util.Iterator;
/**
 * Config: default
 * Some Javadoc.
 *
 * @since something
 */

/**
 * Description.
 *
 * @param <T> Description.  // ok
 * @param <E> Description.  // ok
 */
class InputJavadocParamOrderDefs<T, E> {
    /**
     * Descrp.
     *
     * @param <K> Description.  // ok
     * @param x Description.    // ok
     * @param <K> Description.  // violation
     */
    public <K> int Test(int x, int y) {
        return 9;
    }

        /**
         * Descrip.
         *
         * @param Str desc. // ok, param does not exists
         */
        public String getUserId(String str){
            return "New";
        }

    /**
     * Description.
     *
     * @param <W> Description.     // ok
     * @param message Description. // violation
     * @param type Description.    // violation
     * @param <V> Description.     // violation
     * @return Description.
     */
    <W,V extends T> V convert(String message, Class<W> type) {
        return null;
    }


    /**
     * Description.
     *
     * @param <EE> Description. // ok, param does not exists
     */
    @FunctionalInterface
    static interface Interface1<E> extends Iterable<E> {

        /**
         * Description.
         *
         * @param <W> Description.     // ok
         * @param <V> Description.     // ok
         * @param message Description. // ok
         * @param <type> Description.  // ok, param does not exists
         * @return Description.
         */
        default <W, V extends E> V convert(String message, Class<W> type) {
            return null;
        }
    }

    /**
     * Description.
     *
     * @param a Description.                // ok
     * @param b Description.                // ok
     * @param trapezoidWidth Description.   // ok
     * @return Description.
     */
    private static strictfp double [] quadrature (int a, int b, double... trapezoidWidth) {
        return new double [2];
    }

    /**
     * Descrip.
     *
     * @param <ID> descr.       // ok, param does not exists
     * @param <T> descr.        // ok
     * @param <T> descr.        // ok
     * @param <ID_JP> descr.    // ok, param does not exists
     * @param <T> descr.        // violation
     * @param <U> descr.        // ok
     */
    public interface Service<T,U> {
    T executeService(U... args);
    }

    /**
     * Descrip.
     *
     * @param <ID> descr.          // ok, param does not exists
     * @param <Integer> descr.     // ok
     * @param <OP_JP> descr.       // ok, param does not exists
     * @param <Integer> descr.     // violation
     */
    public class MyService<String, Integer> implements Service<String, Integer> {

        /**
         * Descrip.
         *
         * @param <ID> descr.        // ok, param does not exists
         * @param <args> descr.      // ok
         * @param <ID_JP> descr.     // ok, param does not exists
         */
        @Override
        public String executeService(Integer... args) {
            // do stuff
            return null;
        }
    }

    /**
     * Descrip.
     *
     * @param <TT> desrc. // ok, param does not exists
     */
    interface Addable<T> {
    }
}
