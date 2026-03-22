/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = (default)
ignoreFinal = false
ignoreStatic = false
ignoreStaticFinal = false
ignoreOverriddenMethods = false
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

abstract class InputAbbreviationAsWordInNameType6Part2{}

class StateX6 {
    int userID; // violation
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation16 {
    String VALUE = "value"; // violation
}

@interface Annotation26 {
    static String VALUE = "value"; // violation
}

@interface Annotation36 {
    final String VALUE = "value"; // violation
}

@interface Annotation46 {
    final static String VALUE = "value"; // violation
}
