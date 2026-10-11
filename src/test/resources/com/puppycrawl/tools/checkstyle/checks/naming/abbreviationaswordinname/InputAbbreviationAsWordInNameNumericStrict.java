/*
AbbreviationAsWordInName
allowedAbbreviationLength = 0
allowedAbbreviations = SLF4,SLF4J,UTF8
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = METHOD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameNumericStrict {
    void getSLF4JLogger() { }
    void getSLF4Adapter() { }
    void getUTF8() { }
    void getSLF4JUTF8() { }
    void getSLF4JXMLParser() { } // violation 'Abbreviation in name'
    void getSlF4JLogger() { } // violation 'Abbreviation in name'
    void getSLF5JLogger() { } // violation 'Abbreviation in name'
    void getUTF16() { } // violation 'Abbreviation in name'
}
