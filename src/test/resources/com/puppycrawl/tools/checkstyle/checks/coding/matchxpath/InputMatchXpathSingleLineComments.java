/*
MatchXpath
query = //SINGLE_LINE_COMMENT

*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathSingleLineComments {
    void foo() {
        // Some Comment // violation
        int num; // Trailing comment // violation
    }
}
