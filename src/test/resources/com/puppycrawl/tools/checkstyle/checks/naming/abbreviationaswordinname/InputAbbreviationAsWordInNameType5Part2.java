/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = CLASS
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, \
         INTERFACE_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameType5Part2 {
    class StateX5 {
        int userID;
        int scaleX, scaleY, scaleZ;

        int getScaleX() {
        return this.scaleX;
    }
    }

    @interface Annotation15 {
        String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation25 {
        static String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation35 {
        final String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation45 {
        final static String VALUE = "value"; // in @interface this is final/static
    }
}
