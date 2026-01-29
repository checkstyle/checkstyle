/*
IllegalSymbol
symbolCodes = 0x1F600-0x1F610-0x1F620
asciiOnly = (default)false
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolMultipleDashes {
    int x = 5; // 😀
}
