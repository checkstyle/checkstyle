/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
enforceWhitespaceAroundArrayInit = true
ignoreEnhancedForColon = (default)true
tokens = ARRAY_INIT, RCURLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundAnnotationArrayInit {

    @interface MyAnnotation {
        String[] value() default {"a", "b"};
        // violation above ''}' is not preceded with whitespace'
    }

    // violation below ''}' is not preceded with whitespace'
    @SuppressWarnings({"unchecked"}/* comment */)
    public void method1() {
        int x = 0;
    }

    @SuppressWarnings({ "unchecked" })
    public void method2() {
        int x = 0;
    }
}
