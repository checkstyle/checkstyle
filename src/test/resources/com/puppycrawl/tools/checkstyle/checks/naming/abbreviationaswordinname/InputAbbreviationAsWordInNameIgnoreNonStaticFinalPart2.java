/*
AbbreviationAsWordInName
allowedAbbreviationLength = 4
allowedAbbreviations = MARAZMATIC, VARIABLE
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameIgnoreNonStaticFinalPart2 {
    class StateX {
        int userID;
        int scaleX, scaleY, scaleZ;

        int getScaleX() {
            return this.scaleX;
        }
    }

    @interface Annotation1 {
        String VALUEEEE = "value"; // violation
    }

    @interface Annotation2 {
        static String VALUEEEE = "value"; // violation
    }

    @interface Annotation3 {
        final String VALUEEEE = "value"; // violation
    }

    @interface Annotation4 {
        final static String VALUEEEE = "value"; // violation
    }

    final class InnerClassOneVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

    static class InnerClassTwoVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

    static final class InnerClassThreeVIOLATION { // violation
        // only variable definitions are affected by ignore static/final properties
    }

}
