package org.checkstyle.suppressionxpathfilter.nowhitespacebeforecasedefaultcolon;

public class InputXpathNoWhitespaceBeforeCaseDefaultAndColonOnSameLine {
    {
        switch(1) {
            case 1:
                break;
            case 2:
                break;
            default : // warn
                break;
        }
    }
}
