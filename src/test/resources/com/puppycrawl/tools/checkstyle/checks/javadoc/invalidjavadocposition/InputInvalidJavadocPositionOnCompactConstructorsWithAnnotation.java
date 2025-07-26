/*
InvalidJavadocPosition


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.invalidjavadocposition;

public class InputInvalidJavadocPositionOnCompactConstructorsWithAnnotation {

    @interface SizeType {
        int max();
    }

    /**
     * Something something.
     *
     * @param containerPath a path on the container
     */
    @SizeType(max = 2)
    record BindMount(String containerPath) {

        /**
         * Creates a mount.
         *
         * @param containerPath a path on the container
         */
        @SizeType(max = 1)
        BindMount {}
    }

    /**
     * Something something.
     *
     * @param fileSystem a path on the container
     */
    @SizeType(max = 4)
    public record MyRecord(String fileSystem) {

        /**
         * Creates a mount.
         *
         * @param fileSystem a path on the container
         */
        @SizeType(max = 2)
        public MyRecord {}
        /** some invalid javadoc */
        // violation above 'Javadoc comment is placed in the wrong location.'
    }

    /**
     * Maps the ports.
     *
     * @param from the from param
     */
    @SizeType(max = 3)
    record Mapping(String from) {

        /**
         * The constructor for Mapping.
         *
         * @param from The source
         */
        @SizeType(max = 3)
        Mapping(String from) {
            this.from = from;
        }
    }

    /**
     * Maps the ports.
     *
     * @param to the param
     */
    @SizeType(max = 3)
    public record Mapping2(String to) {

        /**
         * The constructor for Mapping.
         *
         * @param to The source
         */
        @SizeType(max = 3)
        public Mapping2(String to) {
            this.to = to;
        }
    }
}
