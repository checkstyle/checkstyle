/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
allowEmptyInitializers = true
ignoreEnhancedForColon = (default)true
tokens = RCURLY, SLIST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundEmptyInitializersAllowed {

    static {} int a;
    static {int b = 1;} int c;
    // 2 violations above:
    // ''{' is not followed by whitespace'
    // ''}' is not preceded with whitespace'

    {}{}
    void method() {
        int num = 1;
        {}

        // violation below ''{' is not followed by whitespace'
        {int left = 1;}
        // violation above ''}' is not preceded with whitespace'
    }
}
