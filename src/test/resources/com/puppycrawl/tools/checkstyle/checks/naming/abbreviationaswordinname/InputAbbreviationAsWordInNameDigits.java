/*
AbbreviationAsWordInName
allowedAbbreviationLength = 1
allowedAbbreviations = JC,SC,SLF4J,GUI,IO,TPS,XML
ignoreFinal = false
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

interface InputAbbreviationAsWordInNameDigits {
    String getSLF4JLogger();
}

class InputAbbreviationAsWordInNameDigitsHelper {

    String mySLF4JField;

    // violation below 'Abbreviation in name 'myHTMLField''
    String myHTMLField;

    public String getSLF4JLogger() {
        return null;
    }

    public String getSLF4J2Logger() {
        return null;
    }

    public String getSLF4JSLF4JLogger() {
        return null;
    }

    public String getXSLF4JLogger() {
        return null;
    }

    public String getXMLSLF4JLogger() {
        return null;
    }

    // violation below 'Abbreviation in name 'getABCDESLF4JLogger''
    public String getABCDESLF4JLogger() {
        return null;
    }

    // violation below 'Abbreviation in name 'getSLF4JDKLogger''
    public String getSLF4JDKLogger() {
        return null;
    }

    // violation below 'Abbreviation in name 'getSLF4jLogger''
    public String getSLF4jLogger() {
        return null;
    }

    // violation below 'Abbreviation in name 'getHTMLParser''
    public String getHTMLParser() {
        return null;
    }

    static class SLF4JAdapter {
    }

    // violation below 'Abbreviation in name 'UTF8Codec''
    static class UTF8Codec {
    }
}
