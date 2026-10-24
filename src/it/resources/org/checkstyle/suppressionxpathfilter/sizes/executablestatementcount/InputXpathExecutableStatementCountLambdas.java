/*
ExecutableStatementCount
max = 1
tokens = LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.sizes.executablestatementcount;

import java.util.function.Consumer;
import java.util.function.Function;

public class InputXpathExecutableStatementCountLambdas {
    Consumer c = (s) -> { // violation 'Executable statement count'
        String str = s.toString();
        str = str + "!";
    };

    Consumer t = a -> a.toString().trim();
    Function x1 = a -> a;
    Function y = a -> null;
}
