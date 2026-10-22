/*
OpenjdkMethodParameterAlignment


*/

package org.checkstyle.suppressionxpathfilter.whitespace.openjdkmethodparameteralignment;

public class InputXpathOpenjdkMethodParameterAlignmentAnnotated {

    // violation 2 lines below 'Only one parameter is allowed per line in a vertical list.'
    @Deprecated
    void foo(int a,
             int b, int c) {
    }
}
