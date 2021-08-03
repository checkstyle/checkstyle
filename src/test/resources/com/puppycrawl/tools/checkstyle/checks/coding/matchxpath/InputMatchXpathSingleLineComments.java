/*
MatchXpath
query = //SINGLE_LINE_COMMENT[./COMMENT_CONTENT[not(starts-with(@text, ' '))]]

*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathSingleLineComments {
    void foo() {
        int num; // Trailing comment // ok

        boolean isTrue; //Some Comment // violation
        double pi = 3.14; //Constant PI value // violation
    }
}
