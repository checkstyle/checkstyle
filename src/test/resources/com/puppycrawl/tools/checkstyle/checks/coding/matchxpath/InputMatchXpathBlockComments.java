/*
MatchXpath
query = //BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT[contains(@text, '{') \
        and not(starts-with(@text, '\\nMatchXpath'))]]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathBlockComments {
    /* void foo() {} */ // violation 'Illegal code structure detected'

    /* public class // violation 'Illegal code structure detected'
        {
            void foo2() {}
        }
     */
}
