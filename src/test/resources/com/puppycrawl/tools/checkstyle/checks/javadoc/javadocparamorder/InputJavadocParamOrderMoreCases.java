/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderMoreCases {
    /** Some javadoc */
    class NonDeclarationContext {
        /** Some javadoc */
        String field;
    }

    /**
     * @param p1 first
     * @param p2 second
     */
    protected String protectedMethodWithModifiers(String p1, String p2) {
        return "";
    }

    /**
     * @param p1 first
     * @param p2 second
     */
    static String staticMethodWithModifiers(String p1, String p2) {
        return "";
    }

    class MethodWithTypeParametersParent {
        /**
         * @param <T> type param
         * @param p1 first param
         */
        <T> String genericMethod(String p1) {
            return "";
        }

        /**
         * @param p1 first param
         * @param <T type param
         */
        <T> String genericMethodWithTypeParamAfterParams(String p1) {
            return "";
        }

        /**
         * @param T> type param
         * @param p1 first param
         */
        <T> String genericMethodWithTypeParamBeforeParams(String p1) {
            return "";
        }

        /**
         * @param <T> type param
         * @param <U> second type param
         * @param p1 first param
         */
        <T, U> String genericMethodMultipleTypes(String p1) {
            return "";
        }
    }

    String methodWithNoParameters() {
        return "";
    }

    /**
     * @param p1 first
     */
    String methodWithNoParameters2() {
        return "";
    }

    /**
     * @param
     */
    String methodWithEmptyJavadocParamTag(String p1) {
        return "";
    }

    String nullIdent = null;
    String methodWithNullIdent(String nullIdent) {
        return "";
    }

        public class InputJavadocParamOrderNoNormalParams {
        /**
         * Generic method with no normal parameters - only type parameters.
         * Tests: if (params != null) being false.
         *
         * @param <T> the type
         */
        <T> void genericNoParams() {
        }

        /**
         * Another generic with no params.
         *
         * @param <K> key
         * @param <V> value
         */
        <K, V> void mapMethodNoParams() {
        }

        /**
         * Constructor with type params but no normal params.
         *
         * @param <T> type
         */
        <T> InputJavadocParamOrderNoNormalParams() {
        }
    }
}
