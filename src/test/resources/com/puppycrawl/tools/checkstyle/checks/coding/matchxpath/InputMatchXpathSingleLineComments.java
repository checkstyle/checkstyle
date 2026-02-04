/*
MatchXpath
query = //SINGLE_LINE_COMMENT[./COMMENT_CONTENT[not(starts-with(@text, ' '))]]

*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpathSingleLineComments {
    void foo() {
        int num; // Trailing comment

        // violation below 'Illegal code structure detected'
        boolean isTrue; //Some Comment
        // violation below 'Illegal code structure detected'
        double pi = 3.14; //Constant PI value
    }
}
