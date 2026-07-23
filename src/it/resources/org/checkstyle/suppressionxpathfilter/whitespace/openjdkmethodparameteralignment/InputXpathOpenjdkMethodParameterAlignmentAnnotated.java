package org.checkstyle.suppressionxpathfilter.whitespace.openjdkmethodparameteralignment;

public class InputXpathOpenjdkMethodParameterAlignmentAnnotated {

    void foo(@Deprecated int a,
             @Deprecated int b, @Deprecated int c) { // warn
    }
}
