package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

public class InputNoWhitespaceBeforeCaseDefaultColon {
    {
        switch(1) {
            case 1 : // violation
                break;
            case 2:
                break;
            default : // violation
                break;
        }
    }
}
