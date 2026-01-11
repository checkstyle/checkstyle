/*
IllegalSymbol
symbolCodes = U+2705
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolUnicodePlus {
    int x = 5;
    int y = 10;
    // Comment with ✅ here // violation 'Illegal symbol detected: '✅''
    int z = 15;
}
