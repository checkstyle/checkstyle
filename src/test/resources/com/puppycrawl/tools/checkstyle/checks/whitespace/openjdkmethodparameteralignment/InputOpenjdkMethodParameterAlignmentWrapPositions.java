/*
OpenjdkMethodParameterAlignment


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

public class InputOpenjdkMethodParameterAlignmentWrapPositions {

    void alignedBeforeComma(int a
                            , int b
                            , int c) {
    }

    void misalignedBeforeComma(int a
                             , int b
                             , int c) {
        // violation 3 lines above 'Parameter should be aligned vertically or indented 8 spaces'
    }

    void twoParametersAfterLeadingComma(int a
                                        , int b, int c) {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
    }

    void commaOnItsOwnLine(int a
                           ,
                           int b) {
    }

    void eightSpacesBeforeComma(int a
            , int b
            , int c) {
    }

    void annotationArgumentSpanningLines(@Names({"a",
                                                 "b"}) int a,
                                         int b) {
    }

    void brokenAfterParenthesisBeforeComma(
            int a
            , int b) {
    }

    void arrayDeclaratorOnItsOwnLine(int a
                                     [], int b) {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
    }

    void qualifiedTypeSpanningLines(int a, java.util
                                            .Map<String, String> b,
                                    int c) {
        // violation 3 lines above 'Only one parameter is allowed per line in a vertical list.'
    }

    @interface Names {

        String[] value();
    }
}
