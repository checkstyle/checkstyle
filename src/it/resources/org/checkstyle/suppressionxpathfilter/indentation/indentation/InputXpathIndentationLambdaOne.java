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

public class InputXpathIndentationLambdaOne {
    void test() {
        MyLambda getA =
        (a) -> a; // violation 'incorrect indentation level 8, expected level should be 12'
    }
}

interface MyLambda {
    int myMethod(int a);
}
