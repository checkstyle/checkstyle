/*
IllegalSymbol
symbolCodes = 0x1F600
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolTextBlock {
    // 😀 // violation 'Illegal symbol detected'
    String tb = """
        Hello World
        """;
}
