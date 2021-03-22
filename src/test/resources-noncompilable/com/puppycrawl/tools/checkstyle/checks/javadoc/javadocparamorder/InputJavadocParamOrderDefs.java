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
class InputJavadocParamOrderDefs<T, E> { // ok
    /**
     * Descrp.
     *
     * @param <K> Description.  // ok
     * @param x Description.    // ok
     * @param <K> Description.  // violation
     */
    public <K>Test(int x, int y) { // violation
        return "Check";
    }

    /**
     * Descrp.
     *
     * @param <E> Description.        // ok
     * @param z Description.          // ok
     * @param numberList Description. // ok
     */
    record Record1<E>(Integer z, Integer... numberList) implements Interface1<String> { // ok

        /**zl
         * Description.
         *
         * @param z Description. // ok
         */
        Record1(Integer z) { // ok
            this(z, 2, 3);
        }

        /**
         * Descrip.
         *
         * @param Str desc. // ok, param does not exists
         */
        @Override
        public Long getUserId(String str){ // ok
            return "New";
        }

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
    <W, V extends T> V convert(String message, Class<W> type) { // violation
        return null;
    }


    /**
     * Description.
     *
     * @param <EE> Description. // ok, param does not exists
     */
    @FunctionalInterface
    static interface Interface1<E> extends Iterable<E> { // ok

        /**
         * Description.
         *
         * @param <W> Description.     // ok
         * @param <V> Description.     // ok
         * @param message Description. // ok
         * @param <type> Description.  // ok, param does not exists
         * @return Description.
         */
        default <W, V extends E> V convert(String message, Class<W> type) { // ok
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
    private static strictfp double [] quadrature (int a, int b, double... trapezoidWidth) { // ok
        return new double [2];
    }

    /**
     * Descrip.
     *
     * @param <ID> The type for the identifier of this entity.               // ok
     * @param <ID_JP> The type identifying journey patterns.                 // ok
     * @param <OP_JP> The ordering used for points in journey patterns.      // ok
     * @param <OP_JP> The type of journey pattern referenced by this entity. // ok, duplicate tag
     */
    public interface VehicleJourney<
            ID,
            ID_JP, OP_JP extends Comparable<OP_JP>, JP extends JourneyPattern<ID_JP, OP_JP>
        > extends IdentifiableObject<ID>
    { // ok
        JP getJourneyPattern();
    }

    /**
     * Descrip.
     *
     * @param <TT> desrc. // ok, param does not exists
     */
    interface Addable<T> { // ok
        // DO something;
    }
}
