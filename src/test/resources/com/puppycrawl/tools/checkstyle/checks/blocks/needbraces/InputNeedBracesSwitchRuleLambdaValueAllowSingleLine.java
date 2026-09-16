/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

import java.util.function.Function;

public class InputNeedBracesSwitchRuleLambdaValueAllowSingleLine {

    Function<String, String> notInSwitchRule = Word -> Word.trim();

    static Function<String, String> ruleValue(int x) {
        return switch (x) {
            case 1 -> Word -> Word.trim();
            default -> (Word) -> Word.trim();
        };
    }

    static Function<String, String> multiLineRuleValue(int x) {
        return switch (x) {
            case 1 -> Word -> Word // violation ''->' construct must use '{}'s'
                    .trim();
            default -> throw new IllegalStateException();
        };
    }
}
