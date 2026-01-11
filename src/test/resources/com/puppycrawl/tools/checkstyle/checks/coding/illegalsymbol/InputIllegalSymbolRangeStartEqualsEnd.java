/*
IllegalSymbol
symbolCodes = 0x2705-0x2705
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolRangeStartEqualsEnd {
               // ✅ single-element range // violation 'Illegal symbol detected'
    int x = 1;
}
