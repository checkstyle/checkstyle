/*
IllegalSymbol
symbolCodes = 0x1F600
asciiOnly = (default)false
tokens = STRING_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolStringLiteral {
    String s = "Hello 😀"; // violation 'Illegal Unicode symbol detected'
}
