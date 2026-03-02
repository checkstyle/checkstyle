/*
IllegalSymbol
symbolCodes = 0x2705,,0x274C
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolIgnoreEmpty {
    // This has ✅ // violation 'Illegal symbol detected: '✅''
    int x = 5;
}
