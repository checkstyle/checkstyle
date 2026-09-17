/*
AbbreviationAsWordInName
allowedAbbreviationLength = 4
allowedAbbreviations = SYSTEMATIC, VARIABLE
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
        // violation below 'Abbreviation in name 'VALUELONG''
        String VALUELONG = "value";
    }

    @interface Annotation2 {
        // violation below 'Abbreviation in name 'VALUELONG''
        static String VALUELONG = "value";
    }

    @interface Annotation3 {
        // violation below 'Abbreviation in name 'VALUELONG''
        final String VALUELONG = "value";
    }

    @interface Annotation4 {
        // violation below 'Abbreviation in name 'VALUELONG''
        final static String VALUELONG = "value";
    }

    // violation below 'Abbreviation in name 'InnerClassOneVIOLATION''
    final class InnerClassOneVIOLATION {
        // only variable definitions are affected by ignore static/final properties
    }

    // violation below 'Abbreviation in name 'InnerClassTwoVIOLATION''
    static class InnerClassTwoVIOLATION {
        // only variable definitions are affected by ignore static/final properties
    }

    // violation below 'Abbreviation in name 'InnerClassThreeVIOLATION''
    static final class InnerClassThreeVIOLATION {
        // only variable definitions are affected by ignore static/final properties
    }

}
