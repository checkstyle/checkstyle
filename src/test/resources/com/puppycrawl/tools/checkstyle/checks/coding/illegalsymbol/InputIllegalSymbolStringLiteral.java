/*
IllegalSymbol
symbolCodes = 0x1F600
tokens = STRING_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolStringLiteral {
    String s = "Hello 😀"; // violation 'Illegal symbol detected'
}
