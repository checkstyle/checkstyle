/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

import java.util.function.Function;

public class InputNeedBracesSwitchRuleLambdaValue {

    Function<String, String> f = Word -> Word.trim(); // violation ''->' construct must use '{}'s'

    static Function<String, String> ruleValue(int x) {
        return switch (x) {
            case 1 -> Word -> Word.trim(); // violation ''->' construct must use '{}'s'
            default -> (Word) -> Word.trim(); // violation ''->' construct must use '{}'s'
        };
    }

    static Function<String, String> bracedRuleValue(int x) {
        return switch (x) {
            case 1 -> Word -> {
                return Word.trim();
            };
            default -> throw new IllegalStateException();
        };
    }
}
