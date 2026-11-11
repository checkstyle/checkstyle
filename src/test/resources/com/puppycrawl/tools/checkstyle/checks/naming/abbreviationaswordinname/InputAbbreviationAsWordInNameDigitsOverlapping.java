/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = AB1,AB1CD
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameDigitsOverlapping {

    public String getAB1CDValue() {
        return null;
    }

    public String getAB1Value() {
        return null;
    }

    public String getAB12Value() {
        return null;
    }

    // violation below 'Abbreviation in name 'getAB1CXValue''
    public String getAB1CXValue() {
        return null;
    }
}
