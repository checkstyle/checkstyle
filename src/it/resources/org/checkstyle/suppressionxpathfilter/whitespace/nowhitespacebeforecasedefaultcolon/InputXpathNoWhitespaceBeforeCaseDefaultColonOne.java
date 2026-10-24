/*
NoWhitespaceBeforeCaseDefaultColon


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespacebeforecasedefaultcolon;

public class InputXpathNoWhitespaceBeforeCaseDefaultColonOne {
    {
        switch(1) {
            case 1 : // violation "':' is preceded with whitespace."
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
