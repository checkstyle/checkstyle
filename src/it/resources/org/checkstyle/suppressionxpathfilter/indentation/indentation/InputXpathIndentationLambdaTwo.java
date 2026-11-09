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

interface MyLambdaInterface {
    int foo(int a, int b);
};

public class InputXpathIndentationLambdaTwo {
    void test() {
        MyLambdaInterface div = (a, b)
            -> {
                if(b != 0) {
                    return a/b;
                }
              return 0; // violation 'expected level should be one of the following: 12, 16'
        };
    }
}
