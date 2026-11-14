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

public class InputWhitespaceAroundEnforceArrayInit {

    public final int[] colors1 = new int[] { 5};
    // violation above ''}' is not preceded with whitespace'

    public final int[] colors2 = new int[] {5 };
    // violation above ''{' is not followed by whitespace'

    public final int[] colors3 = new int[] {5};
    // 2 violations above:
    // ''{' is not followed by whitespace'
    // ''}' is not preceded with whitespace'

    public final int[] colors4 = new int[] { 5 };

    public final int[] colors5 = {1, 2};
    // 2 violations above:
    // ''{' is not followed by whitespace'
    // ''}' is not preceded with whitespace'

    public final int[] colors6 = { 1, 2 };

    public final int[] colors7 = new int[]{5 };
    // 2 violations above:
    // ''{' is not followed by whitespace'
    // ''{' is not preceded with whitespace'

    public final int[] colors8 = new int[] {};

    public final int[] colors9 = new int[] { };

    public final int[] colors10 = new int[] { 1, 2,};
    // violation above ''}' is not preceded with whitespace'

    public final int[] colors11 = new int[] { 1, 2, };

    public final int[][] colors12 = {{1, 2}, {3, 4}};
    // 7 violations above:
    // ''{' is not followed by whitespace'
    // ''{' is not followed by whitespace'
    // ''{' is not preceded with whitespace'
    // ''}' is not preceded with whitespace'
    // ''{' is not followed by whitespace'
    // ''}' is not preceded with whitespace'
    // ''}' is not preceded with whitespace'

    public final int[][] colors13 = { { 1, 2 }, { 3, 4 } };

    public final int[] colors14 = new int[] {
        1, 2
    };

    public final int[] colors15 = new int[] {
        1, 2};
    // violation above ''}' is not preceded with whitespace'

    // violation below ''}' is not preceded with whitespace'
    @SuppressWarnings({"unchecked"})
    public void method1() {
        int x = 0;
    }

    @SuppressWarnings({ "unchecked" })
    public void method2() {
        int x = 0;
    }

    // violation below ''}' is not preceded with whitespace'
    @SuppressWarnings(value = {"a", "b"})
    public void method3() {
        int x = 0;
    }
}
