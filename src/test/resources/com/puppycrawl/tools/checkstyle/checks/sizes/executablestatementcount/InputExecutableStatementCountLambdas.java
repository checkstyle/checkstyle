package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

/* Config:
 * max = 0
 * tokens = LAMBDA
 */

import java.util.function.Consumer;

public class InputExecutableStatementCountLambdas {

    Consumer a = (o) -> {
        o.toString(); // 1
        o.toString(); // 2
        o.toString(); // 3
        o.toString(); // 4
        o.toString(); // 5
        o.toString(); // 6
    };

    Consumer b = (x) -> {
        Consumer c = (s) -> {
            String str = s.toString();
            str = str + "!";
        };
        Consumer d = (s) -> {
            String str = s.toString();
            str = str + "?";
        };
    };
}
