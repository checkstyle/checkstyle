/*
ExecutableStatementCount
max = 1
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

import java.util.function.Consumer;
import java.util.function.Function;

public class InputExecutableStatementCountLambdas {

    Consumer a = (o) -> { // violation
        o.toString(); // 1
        o.toString(); // 2
        o.toString(); // 3
        o.toString(); // 4
        o.toString(); // 5
        o.toString(); // 6
    };

    Consumer b = (x) -> { // violation
        Consumer c = (s) -> { // violation
            String str = s.toString();
            str = str + "!";
        };
        Consumer d = (s) -> { // violation
            String str = s.toString();
            Consumer t = a -> a.toString().trim();
            Function x1 = a -> a;
            Function y = a -> null;
        };
    };
}
