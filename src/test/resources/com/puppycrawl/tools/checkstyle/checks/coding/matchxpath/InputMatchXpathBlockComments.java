/* // violation
MatchXpath
query = //BLOCK_COMMENT_BEGIN

*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathBlockComments {

    /* Some Block comments */ // violation
    void foo() {}

    /* package */ void foo2() {} // violation
}
