/*
IllegalSymbol
symbolCodes = 0X2705
asciiOnly = (default)false
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolUppercase0X {
    int x = 5; // ✅ // violation 'Illegal Unicode symbol detected'
}
