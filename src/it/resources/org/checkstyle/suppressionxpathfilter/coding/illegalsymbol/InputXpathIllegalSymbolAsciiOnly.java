/*
IllegalSymbol
symbolCodes = 0x0080-0x10FFFF
tokens = (default)COMMENT_CONTENT


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalsymbol;

public class InputXpathIllegalSymbolAsciiOnly {
    // violation below 'Illegal symbol detected.'
    int x = 5; // café // warn
}
