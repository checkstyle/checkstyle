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

abstract class InputAbbreviationAsWordInNameTypePart2 {}

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
