/*
MatchXpath
query = //BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT[contains(@text, '{') \
        and not(starts-with(@text, '\\nMatchXpath'))]]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathBlockComments {
    // violation below 'Illegal code structure detected'
    /* void foo() {} */

    // violation below 'Illegal code structure detected'
    /* public class
        {
            void foo2() {}
        }
     */
}
