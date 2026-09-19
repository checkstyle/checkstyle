package org.checkstyle.suppressionxpathfilter.naming.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterNameSwitchRule {
    Function<String, String> test(int value) {
        return switch (value) {
            case 0 -> Word -> Word.trim(); // warn
            default -> word -> word.trim();
        };
    }
}
