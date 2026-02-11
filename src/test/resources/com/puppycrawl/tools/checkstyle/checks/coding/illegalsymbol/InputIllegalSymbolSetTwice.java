/*
IllegalSymbol
symbolCodes = 0x1F600
tokens = (default)COMMENT_CONTENT


*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolSetTwice {

    int x = 1; // 😀 // violation 'Illegal symbol detected'
    int y = 2; // ✅
}
