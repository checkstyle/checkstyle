package com.openjdk.checkstyle.test.chapterformatting.rulewrappingmethoddeclarations;

/**
 * Input for method parameter alignment.
 */
public class InputOpenjdkMethodParameterAlignment {

    // violation below 'Only one parameter is allowed per line in a vertical list.'
    void twoOnLastLine(int a,
                       int b, int c) {
    }

    void oneParameterPerLine(int a,
                             int b,
                             int c) {
    }

    void wrappedByEightSpaces(int a, int b,
            int c) {
    }
}
