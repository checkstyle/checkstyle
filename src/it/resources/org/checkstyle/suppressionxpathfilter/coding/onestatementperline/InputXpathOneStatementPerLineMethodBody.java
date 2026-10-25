/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.onestatementperline;

public class InputXpathOneStatementPerLineMethodBody {
    void test() {
        // violation below 'Only one statement per line allowed.'
        int a = 1; int b = 2;
    }
}
