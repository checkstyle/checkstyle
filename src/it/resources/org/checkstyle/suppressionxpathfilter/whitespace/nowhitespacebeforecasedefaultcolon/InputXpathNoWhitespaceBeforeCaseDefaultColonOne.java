package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebeforecasedefaultcolon;

public class InputXpathNoWhitespaceBeforeCaseDefaultColonOne {
    {
        switch(1) {
            case 1 : // warn
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
