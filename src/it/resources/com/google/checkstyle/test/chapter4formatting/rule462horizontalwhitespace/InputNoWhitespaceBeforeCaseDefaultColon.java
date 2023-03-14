package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputNoWhitespaceBeforeCaseDefaultColon {
    {
        switch(1) {
            case 1 : // warn
                break;
            case 2:
                break;
            default : // warn
                break;
        }
    }
}
