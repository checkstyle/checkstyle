/*
AbbreviationAsWordInName
allowedAbbreviationLength = 4
allowedAbbreviations = CLASS, FACTORY
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameType3Part2 {
    class StateX3 {
        int userID;
        int scaleX, scaleY, scaleZ;

        int getScaleX() {
        return this.scaleX;
    }
    }

    @interface Annotation13 {
        String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation23 {
        static String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation33 {
        final String VALUE = "value"; // in @interface this is final/static
    }

    @interface Annotation43 {
        final static String VALUE = "value"; // in @interface this is final/static
    }
}
