/*
IllegalSymbol
symbolCodes = 0x1F600, 0x1F600-0x1F602
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolOverlap {
    int x = 1; // 😀 //violation 'Illegal symbol detected'
}
