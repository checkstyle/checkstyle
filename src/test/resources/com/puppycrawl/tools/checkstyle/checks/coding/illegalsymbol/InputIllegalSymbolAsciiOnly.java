/*
IllegalSymbol
symbolCodes = (default)
asciiOnly = true
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolAsciiOnly {
    int x = 1; // café // violation 'Illegal Unicode symbol detected'
    int y = 2; // • test // violation 'Illegal Unicode symbol detected'
}
