/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterNameSwitchRule {
    Function<String, String> test(int value) {
        return switch (value) {
            // violation below 'Name 'Word' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.'
            case 0 -> Word -> Word.trim();
            default -> word -> word.trim();
        };
    }
}
