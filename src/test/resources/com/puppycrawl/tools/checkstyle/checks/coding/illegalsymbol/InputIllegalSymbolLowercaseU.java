/*
IllegalSymbol
symbolCodes = u+2705
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolLowercaseU {
    int x = 5; // ✅ // violation 'Illegal symbol detected: '✅''
}
