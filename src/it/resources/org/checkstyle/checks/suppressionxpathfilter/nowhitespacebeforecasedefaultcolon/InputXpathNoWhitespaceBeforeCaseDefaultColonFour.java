package org.checkstyle.checks.suppressionxpathfilter.nowhitespacebeforecasedefaultcolon;

public class InputXpathNoWhitespaceBeforeCaseDefaultColonFour {
    {
        switch(1) {
            case 1:
                break;
            case 2:
                break;
            default
                    : // warn
                break;
        }
    }
}
