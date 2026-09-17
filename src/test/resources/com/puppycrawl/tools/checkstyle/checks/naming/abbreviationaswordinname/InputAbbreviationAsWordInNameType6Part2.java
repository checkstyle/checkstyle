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
    // violation below 'Abbreviation in name 'userID''
    int userID;
    int scaleX, scaleY, scaleZ;

    int getScaleX() {
        return this.scaleX;
    }
}

@interface Annotation16 {
    // violation below 'Abbreviation in name 'VALUE''
    String VALUE = "value";
}

@interface Annotation26 {
    // violation below 'Abbreviation in name 'VALUE''
    static String VALUE = "value";
}

@interface Annotation36 {
    // violation below 'Abbreviation in name 'VALUE''
    final String VALUE = "value";
}

@interface Annotation46 {
    // violation below 'Abbreviation in name 'VALUE''
    final static String VALUE = "value";
}
