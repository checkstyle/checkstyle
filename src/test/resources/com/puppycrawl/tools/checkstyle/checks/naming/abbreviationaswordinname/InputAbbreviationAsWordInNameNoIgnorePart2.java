/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = NUMBER, MARAZMATIC, VARIABLE
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = false
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameNoIgnorePart2 {
    class StateX {
        int userID;
        int scaleX, scaleY, scaleZ;

        int getScaleX() {
            return this.scaleX;
        }
    }

    @interface Annotation1 {
        String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation2 {
        static String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation3 {
        final String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation4 {
        final static String VALUE = "value"; // in @interface this is final/static
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
