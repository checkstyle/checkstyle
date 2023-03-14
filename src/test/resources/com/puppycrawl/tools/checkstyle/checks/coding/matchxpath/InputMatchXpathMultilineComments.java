/*
MatchXpath
query = //BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT[contains(@text, '\\n    Forbidden comment\\n') \
        and not(starts-with(@text, '\\nMatchXpath'))]]


*/


package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathMultilineComments {

    /* // violation
    Forbidden comment
    Some other comment
     */
    void foo1() {}

    /* // violation
    Some other comment
    Forbidden comment
    */
    void foo2() {}
}
