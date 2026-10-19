
package com.puppycrawl.tools.checkstyle.utils.blockcommentposition;

@interface Marker {
}

public record InputBlockCommentPositionOnRecordComponent(
        /**
         * I'm a javadoc
         */
        String first,
        /**
         * I'm a javadoc
         */
        String second) {
}

record InputBlockCommentPositionOnRecordComponentOne(
        /** I'm a javadoc */
        int value) {
}

record InputBlockCommentPositionOnRecordComponentTwo(
        /**
         * I'm a javadoc
         */
        @Marker String value) {
}

record InputBlockCommentPositionOnRecordComponentThree(
        @Marker
        /**
         * I'm a javadoc
         */
        String value) {
}

record InputBlockCommentPositionOnRecordComponentFour(
        /**
         * I'm a javadoc
         */
        java.lang.String value) {
}

record InputBlockCommentPositionOnRecordComponentFive(
        /**
         * I'm a javadoc
         */
        java.util.List<String> value) {
}

record InputBlockCommentPositionOnRecordComponentSix(
        /**
         * I'm a javadoc
         */
        int[] value) {
}

record InputBlockCommentPositionOnRecordComponentSeven(
        /**
         * I'm a javadoc
         */
        String... value) {
}

record InputBlockCommentPositionOnRecordComponentOuter(int other) {

    record Inner(
            /**
             * I'm a javadoc
             */
            int value) {
    }
}
