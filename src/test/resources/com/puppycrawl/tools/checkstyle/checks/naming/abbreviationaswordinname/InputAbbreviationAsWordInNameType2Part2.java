/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = III
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType2Part2 {}

class StateX2 {
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation12 {
    String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation22 {
    static String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation32 {
    final String VALUE = "value"; // in @interface this is final/static
}

@interface Annotation42 {
    final static String VALUE = "value"; // in @interface this is final/static
}
