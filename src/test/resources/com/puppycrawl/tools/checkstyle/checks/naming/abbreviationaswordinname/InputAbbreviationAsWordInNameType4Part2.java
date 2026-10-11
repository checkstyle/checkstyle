/*
AbbreviationAsWordInName
allowedAbbreviationLength = 5
allowedAbbreviations = CLASS
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameType4Part2 {}

class StateX4 {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation14 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation24 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation34 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation44 {
    final static String VALUE = "value"; // in @interface this is final/static
}
