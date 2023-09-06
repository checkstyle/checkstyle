/*
MatchXpath
query = //BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT[contains(@text, '{') \
        and not(starts-with(@text, '\\nMatchXpath'))]]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathBlockComments {
    /* void foo() {} */ // violation

    /* public class // violation
        {
            void foo2() {}
        }
     */
}
