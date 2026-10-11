/*
IllegalSymbol
symbolCodes = 0x1F600-0x1F64F
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolRangeBoundaries {

    int a = 1; // 😀  // violation 'Illegal symbol detected: '😀''
    int b = 2; // 😁  // violation 'Illegal symbol detected: '😁''
    int c = 3; // 🙏  // violation 'Illegal symbol detected: '🙏''

    int d = 4; // @
    int e = 5; // 🚀
}

