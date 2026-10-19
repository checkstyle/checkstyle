/*
InvalidJavadocPosition


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

public class InputInvalidJavadocPositionOnRecordComponentsWithAnnotation {

    @interface Tag {
    }

    @interface Marker {
    }

    /** A record with a javadoc before the annotation on a component. */
    public record BeforeAnnotation(
            /**
             * The x coordinate.
             */
            @Tag String x) {
    }

    /** A record with a javadoc after the annotation on a component. */
    public record AfterAnnotation(
            @Tag
            /**
             * The x coordinate.
             */
            String x) {
    }

    /** A record with two annotations and a javadoc before them on a component. */
    public record TwoAnnotations(
            /**
             * The value.
             */
            @Tag @Marker String value) {
    }

    /** A record with an annotation only on a component. */
    public record AnnotationOnly(
            @Tag String x) {
    }

    /** A record with a misplaced javadoc inside an annotation on a component. */
    public record InsideAnnotation(
            // violation below 'Javadoc comment is placed in the wrong location.'
            @ /** Misplaced javadoc. */ Tag String y) {
    }
}
