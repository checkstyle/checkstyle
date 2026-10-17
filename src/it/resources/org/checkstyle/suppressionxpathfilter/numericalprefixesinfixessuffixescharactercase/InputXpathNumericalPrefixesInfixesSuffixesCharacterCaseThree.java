/*
NumericalPrefixesInfixesSuffixesCharacterCase


*/

package org.checkstyle.suppressionxpathfilter.numericalprefixesinfixessuffixescharactercase;

public class InputXpathNumericalPrefixesInfixesSuffixesCharacterCaseThree {
    // violation below 'Numerical infix should be in lowercase.'
    float a = 1.2E3F;
    float b = 1.2e3f;
}
