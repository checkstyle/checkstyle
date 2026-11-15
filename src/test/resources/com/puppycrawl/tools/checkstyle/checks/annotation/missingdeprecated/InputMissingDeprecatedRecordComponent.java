/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

public class InputMissingDeprecatedRecordComponent {

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
    }

    /** A record with a single line javadoc on a component. */
    public record SingleLine(
            /** The x coordinate. */
            int x) {
    }
}
