/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4


*/

package org.checkstyle.suppressionxpathfilter.indentation.indentation;

public class InputXpathIndentationElseWithoutCurly {
    void test() {
        if (true)
            exp();
        else exp();

        if (true)
            exp();
        else
        exp(); // violation 'incorrect indentation level 8, expected level should be 12'
    }
    void exp() {}
}
