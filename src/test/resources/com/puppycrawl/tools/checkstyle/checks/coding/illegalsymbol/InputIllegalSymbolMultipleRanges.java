/*
IllegalSymbol
symbolCodes = 0x2705, 0x1F600-0x1F64F, 0x00A9
asciiOnly = (default)false
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolMultipleRanges {
    int x1 = 1; // ✅ // violation 'Illegal Unicode symbol detected'
    int x2 = 2; // 😀 // violation 'Illegal Unicode symbol detected'
    int x3 = 3; // © // violation 'Illegal Unicode symbol detected'
}
