package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

/* Config:
 * max = 1
 * tokens = LAMBDA
 */

import java.util.function.Consumer;

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
        };
    };
}
