/*
IllegalSymbol
symbolCodes = (default)0x2190-0x27BF, 0x1F600-0x1F64F, 0x1F680-0x1F6FF, 0x1F700-0x1FFFFF
tokens = (default)COMMENT_CONTENT


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalsymbol;

public class InputIllegalSymbolDefaultStrong {
    // 😀 First emoji from 0x1F600 range // violation 'Illegal symbol detected: '😀''
    // 🚀 Second emoji from 0x1F680 range // violation 'Illegal symbol detected: '🚀''
    int x = 1;
}
