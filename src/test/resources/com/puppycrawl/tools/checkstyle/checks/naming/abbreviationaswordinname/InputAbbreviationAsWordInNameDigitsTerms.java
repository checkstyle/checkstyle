/*
AbbreviationAsWordInName
allowedAbbreviationLength = 1
allowedAbbreviations = PIXEL3A,UTF8,ML
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameDigitsTerms {

    public String getPIXEL3APrice() {
        return null;
    }

    public String getUTF8() {
        return null;
    }

    // violation below 'Abbreviation in name 'getPIXEL3BPrice''
    public String getPIXEL3BPrice() {
        return null;
    }

    // violation below 'Abbreviation in name 'getXMLValue''
    public String getXMLValue() {
        return null;
    }

    static class UTF8Encoder {
    }
}
