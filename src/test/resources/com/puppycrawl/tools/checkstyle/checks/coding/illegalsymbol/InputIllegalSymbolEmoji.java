/*
IllegalSymbol
symbolCodes = 0x2705, 0x274C
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolEmoji {
    int x = 1; // ✅ Test // violation 'Illegal symbol detected'
    int y = 2; // ❌ Test // violation 'Illegal symbol detected'
}
