/*
IllegalSymbol
symbolCodes = 0x1F600-0x1F64F
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolRangeEnd {
    // Exactly 0x1F64F (end of range)
    int x = 1; // 🙏 // violation 'Illegal symbol detected'
}
