/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

public class InputNoWhitespaceBeforeWithEmoji {
    private String[] _mVar0 = {
        "😃😉🙈" ,  // violation
        "😃 😉 🙈" ,  // violation
        "😃 😉 🙈",  // ok
        "😃😉🙈"  // ok
    };
    private String _mVar1 = "😃😉🙈"; // ok
    private String _mVar2 = "😃 😉 🙈"; // ok
    private String _mVar3 = "😃 😉 🙈" ; // violation
    private String _mVar4 = "a b c" ; // violation
}
