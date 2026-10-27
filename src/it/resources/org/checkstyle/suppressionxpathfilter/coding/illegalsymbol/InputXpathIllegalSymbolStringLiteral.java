/*
IllegalSymbol
symbolCodes = 0x2705
tokens = STRING_LITERAL


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalsymbol;

public class InputXpathIllegalSymbolStringLiteral {
    // violation below 'Illegal symbol detected: '✅'.'
    String value = "Hello ✅"; // warn
}
