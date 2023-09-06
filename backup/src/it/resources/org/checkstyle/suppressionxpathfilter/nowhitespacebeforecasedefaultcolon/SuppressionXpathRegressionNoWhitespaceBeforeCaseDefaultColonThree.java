package org.checkstyle.suppressionxpathfilter.nowhitespacebeforecasedefaultcolon;

public class SuppressionXpathRegressionNoWhitespaceBeforeCaseDefaultColonThree {
    {
        switch(1) {
            case 1
                    : // warn
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
