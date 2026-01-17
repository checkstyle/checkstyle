/*
IllegalSymbol
symbolCodes = \\u2705
asciiOnly = (default)false
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolBackslashU {
    int x = 5; // ✅ // violation 'Illegal Unicode symbol detected'
}
