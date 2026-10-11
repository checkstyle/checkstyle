/*
AbbreviationAsWordInName
allowedAbbreviationLength = 1
allowedAbbreviations = SLF4J,PIXEL3A,SLF4,UTF8
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = METHOD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

class InputAbbreviationAsWordInNameNumeric {
    void getSLF4JLogger() { }
    void getPIXEL3APrice() { }
    void getSLF4Handler() { }
    void getUTF8() { }
    void SLF4J() { }
    void getSLF4JAndUTF8() { }
    void getSLF4JSLF4J() { }
    void getSLF4J_XMLParser() { } // violation 'Abbreviation in name'
    void getXMLSLF4JLogger() { } // violation 'Abbreviation in name'
    void getSLF4JXMLParser() { } // violation 'Abbreviation in name'
    void getSLF5JLogger() { } // violation 'Abbreviation in name'
    void getPIXEL3BPrice() { } // violation 'Abbreviation in name'
    void getXMLParser() { } // violation 'Abbreviation in name'
}
