/*
InvalidJavadocPosition


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

/**
 * Some description.
 */
public class InputInvalidJavadocPositionOnCompactConstructors {

    /**
     * The configuration of a bind mount.
     *
     * @param containerPath a path on the container
     * @param options       mounting options
     */
    record BindMount(String containerPath, String... options) {

        /**
         * Creates a mount.
         *
         * @param containerPath a path on the container
         * @param options       mounting options
         * @throws NullPointerException     if any of the arguments are null
         */
        BindMount {
        }
    }

    /**
     * The configuration.
     *
     * @param text the text
     */
    public record MyRecord(String text) {

        // violation 2 lines below 'Javadoc comment is placed in the wrong location.'
        public MyRecord {}
        /** invalid comment. */
    }

    /**
     * Maps the ports.
     *
     * @param from the from param
     * @param to   the to param
     */
    record Mapping(String from, String to) {

        // violation below 'Javadoc comment is placed in the wrong location.'
        /** some javadoc. */
        /**
         * The constructor for Mapping.
         *
         * @param from The source
         * @param to   The destination
         */
        Mapping(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }
}
