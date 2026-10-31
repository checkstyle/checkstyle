/*
OpenjdkLineWrapping


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

class InputOpenjdkLineWrappingArrays {
    void arrays() {
        int[] valid = {1,
                sum(2,
                        3)};
        int[] invalid = {1,
            sum(2, // violation 'incorrect indentation level 12'
                    3)};
        int[] expression = {1,
                                   2};
        int[] sibling = {1,
                         2};
        int[] created = new int[] {1,
                sum(2,
                        3)};
        int[] wrongCreated = new int[] {1,
            sum(2, // violation 'incorrect indentation level 12'
                    3)};
        int[][] nested = {{1,
                2},
                {3,
                        4}};
        int[][] wrongNested = {{1,
            2}, // violation 'incorrect indentation level 12'
                {3,
                    4}}; // violation 'incorrect indentation level 20'
        int[] empty = {};
        int[] single = {1, 2};
        int[] closing = {
                1
        };
        int[] comments = {1,
            // This comment does not set the continuation column.

                2};
        int[] wrongComments = {1,
            // This comment does not excuse four-space wrapping.

            2}; // violation 'incorrect indentation level 12'
        Runnable[] lambdas = {() -> {
            int validBody = 1
                    + 2;
            int wrongBody = 1
                 + 2; // violation 'incorrect indentation level 17'
        },
                () -> {}};
        Object[] objects = {new Object() {
            int validBody = 1
                    + 2;
            int wrongBody = 1
                 + 2; // violation 'incorrect indentation level 17'
        },
                new Object()};
        int[][] aligned = {{1},
                           {2}};
        sum(1,
            2); // violation 'incorrect indentation level 12'
    }

    int sum(int first, int second) {
        return first + second;
    }
}
