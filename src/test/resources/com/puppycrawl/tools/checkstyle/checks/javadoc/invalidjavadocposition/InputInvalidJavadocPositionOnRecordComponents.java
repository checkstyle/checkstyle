/*
InvalidJavadocPosition


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

public class InputInvalidJavadocPositionOnRecordComponents {

    /** A point in 2D space. */
    public record Point(
            /**
             * The x coordinate.
             */
            int x,
            /**
             * The y coordinate.
             */
            int y) {

        /** A label for the point. */
        static final String LABEL = "point";
    }

    /** A record with a single line javadoc on a component. */
    public record SingleLine(
            /** The x coordinate. */
            int x) {
    }

    /** An outer record with a nested record. */
    public record Outer(int a) {

        /** An inner record. */
        public record Inner(
                /**
                 * The b value.
                 */
                int b) {
        }
    }

    /** A record with a documented compact constructor. */
    public record WithCompact(
            /**
             * The x coordinate.
             */
            int x) {

        /** A compact constructor. */
        public WithCompact {
        }
    }

    /** A record with qualified and generic component types. */
    public record Qualified(
            /**
             * The name.
             */
            java.lang.String name,
            /**
             * The list.
             */
            java.util.List<String> list) {
    }

    /** A record with a misplaced javadoc in the middle of a component type. */
    public record MidType(
            // violation below 'Javadoc comment is placed in the wrong location.'
            int /** Misplaced javadoc. */
            x) {
    }

    /** A record with a misplaced javadoc in the middle of a qualified type. */
    public record MidQualified(
            // violation below 'Javadoc comment is placed in the wrong location.'
            java. /** Misplaced javadoc. */
            lang.String value) {
    }

    /** A class with a misplaced javadoc on a local variable. */
    static class Control {

        void method() {
            // violation below 'Javadoc comment is placed in the wrong location.'
            /** Misplaced javadoc. */
            int local = 0;
        }
    }
}
