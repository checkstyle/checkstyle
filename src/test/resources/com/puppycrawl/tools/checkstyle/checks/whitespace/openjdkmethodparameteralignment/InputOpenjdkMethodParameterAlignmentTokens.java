/*
OpenjdkMethodParameterAlignment
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

public class InputOpenjdkMethodParameterAlignmentTokens {

    InputOpenjdkMethodParameterAlignmentTokens(int a,
                                               int b, int c) {
    }

    void method(int a,
                int b, int c) {
        // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
    }
}
