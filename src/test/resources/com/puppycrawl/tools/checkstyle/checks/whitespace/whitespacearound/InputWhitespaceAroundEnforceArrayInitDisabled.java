/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
allowEmptySwitchBlockStatements = (default)false
enforceWhitespaceAroundArrayInit = (default)false
ignoreEnhancedForColon = (default)true
tokens = ARRAY_INIT, RCURLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundEnforceArrayInitDisabled {

    public final int[] colors1 = new int[] { 5};

    public final int[] colors2 = new int[] {5 };

    public final int[] colors3 = new int[] {5};

    public final int[] colors4 = {1, 2};

    public final int[] colors5 = new int[]{5 };
    // violation above ''{' is not preceded with whitespace'

    public final int[] colors6 = new int[] { 1, 2,};

    public final int[] colors7 = new int[] {
        1, 2};

    @SuppressWarnings({"unchecked"})
    public void method1() {
        int x = 0;
    }
}
