/*
IllegalSymbol
symbolCodes = 0x1F600
asciiOnly = (default)false
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolTextBlock {
    // 😀 // violation 'Illegal Unicode symbol detected'
    String tb = """
        Hello World
        """;
}
