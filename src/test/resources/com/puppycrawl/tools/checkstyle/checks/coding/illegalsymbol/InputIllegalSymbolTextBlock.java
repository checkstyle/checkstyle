/*
IllegalSymbol
symbolCodes = 0x1F600-0x1F64F
tokens = TEXT_BLOCK_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolTextBlock {
    // violation below, 'Illegal symbol detected'
    String s = """
            😀 emoji in text block
        """;
}
