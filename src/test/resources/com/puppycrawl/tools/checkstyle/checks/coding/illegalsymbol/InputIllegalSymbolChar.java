/*
IllegalSymbol
symbolCodes = 0x00E9
asciiOnly = (default)false
tokens = CHAR_LITERAL


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolChar {
    char c = 'é'; // violation 'Illegal Unicode symbol detected'
}
