/*
NoWhitespaceBeforeCaseDefaultColon



*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebeforecasedefaultcolon;

public class InputNoWhitespaceBeforeCaseDefaultColonSupplementaryCharacters {
    enum Letter { 𝒜, 𝒜𝒜, B }

    void strings(String s) {
        switch (s) {
            case "😀": break;
            case "😀😀" : break; // violation '':' is preceded with whitespace.'
            default: break;
        }
    }

    void enums(Letter letter) {
        switch (letter) {
            case 𝒜: break;
            case 𝒜𝒜 : break; // violation '':' is preceded with whitespace.'
            default: break;
        }
    }
}
