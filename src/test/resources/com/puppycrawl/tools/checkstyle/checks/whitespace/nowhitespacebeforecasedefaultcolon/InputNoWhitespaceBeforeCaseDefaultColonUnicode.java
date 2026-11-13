/*
NoWhitespaceBeforeCaseDefaultColon


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

class InputNoWhitespaceBeforeCaseDefaultColonUnicode {
    void test(String value) {
        switch (value) {
            case "😀": break;
            case "😂" : break; // violation '':' is preceded with whitespace.'
            case "😀😀": break;
            case "😂😂"  : break; // violation '':' is preceded with whitespace.'
            case "a": break;
            case "b" : break; // violation '':' is preceded with whitespace.'
            default: break;
        }
    }
}
