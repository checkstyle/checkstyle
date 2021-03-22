//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

import java.util.Iterator;

/**
 * Description.
 *
 * @param <T> Description.
 * @param <E> Description.
 */
class InputJavadocParamOrderDefs<T, E> {
    /**
     * Descrp.
     *
     * @param <K> Description.
     * @param x Description.
     * @param <K> Description.
     */
    public <K>Test(int x, int y) {

    }

    /**
     * Descrp.
     *
     * @param <E> Description.
     * @param z Description.
     * @param numberList Description.
     */
    record Record1<E>(Integer z, Integer... numberList) implements Interface1<String> {

        /**
         * Description.
         *
         * @param z Description.
         */
        Record1(Integer z) {
            this(z, 2, 3);
        }

        /**
         * Descrip.
         *
         * @param strr desc.
         */
        @Override
        public Long getUserId(String str){return "New";}

    }

    /**
     * Description.
     *
     * @param <W> Description.
     * @param message Description.
     * @param type Description.
     * @param <V> Description.
     * @return Description.
     */
    <W, V extends T> V convert(String message, Class<W> type) {
        return null;
    }


    /**
     * Description.
     *
     * @param <EE> Description.
     */
    @FunctionalInterface
    static interface Interface1<E> extends Iterable<E> {
        /**
         * Description.
         *
         * @param <W> Description.
         * @param <V> Description.
         * @param message Description.
         * @param <type> Description.
         * @return Description.
         */
        default <W, V extends E> V convert(String message, Class<W> type) {
            return null;
        }
    }

    /**
     * Description.
     *
     * @param a Description.
     * @param b Description.
     * @param trapezoidWidth Description.
     * @return Description.
     */
    private static strictfp double [] quadrature (int a, int b, double... trapezoidWidth) {
        return new double [2];
    }

    /**
     * Descrip.
     *
     * @param <ID> The type for the identifier of this entity.
     * @param <ID_JP> The type identifying journey patterns.
     * @param <OP_JP> The ordering used for points in journey patterns.
     * @param <OP_JP> The type of journey pattern referenced by this entity.
     */
    public interface VehicleJourney<
            ID,
            ID_JP, OP_JP extends Comparable<OP_JP>, JP extends JourneyPattern<ID_JP, OP_JP>
        > extends IdentifiableObject<ID>
    {
        JP getJourneyPattern();
    }

    /**
     * Descrip.
     *
     * @param <TT> The type for the identifier of this entity.
     */
    interface Addable<T> {
        // DO something;
    }
}
