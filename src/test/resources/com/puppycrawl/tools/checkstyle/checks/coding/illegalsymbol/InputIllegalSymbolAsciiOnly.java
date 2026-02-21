/*
IllegalSymbol
symbolCodes = 0x0080-0x10FFFF
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolAsciiOnly {
    int x = 1; // café // violation 'Illegal symbol detected'
    int y = 2; // • test // violation 'Illegal symbol detected'
}
